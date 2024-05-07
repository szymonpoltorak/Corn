import { AfterViewInit, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { SprintApiService } from '@core/services/api/v1/sprint/sprint-api.service';
import { StorageService } from '@core/services/storage.service';
import { SprintResponse } from '@core/services/api/v1/sprint/data/sprint-response.interface';
import { ProjectMemberApiService } from '@core/services/api/v1/project/member/project-member-api.service';
import { BacklogItemApiService } from '@core/services/api/v1/backlog/item/backlog-item-api.service';
import { BacklogItemStatus } from '@core/enum/BacklogItemStatus';
import { StorageKey } from '@core/enum/storage-key.enum';
import { firstValueFrom } from 'rxjs';
import { SimpleSprint } from '@core/interfaces/boards/reports/simple_sprint.interface';
import { Pageable } from '@core/services/api/utils/pageable.interface';
import { Bucket } from '@core/interfaces/boards/reports/bucket.interface';
import { CanvasJS, CanvasJSAngularChartsModule } from '@canvasjs/angular-charts';

@Component({
    selector: 'app-reports',
    standalone: true,
    imports: [
        CommonModule,
        MatInputModule,
        MatMenuModule,
        MatButtonModule,
        MatIconModule,
        CanvasJSAngularChartsModule,
    ],
    templateUrl: './reports.component.html',
})
export class ReportsComponent implements AfterViewInit {

    protected currentSprint: SimpleSprint | null | undefined = undefined;
    protected previousSprint: SimpleSprint | null = null;
    protected nextSprint: SimpleSprint | null = null;

    protected chartOptions = {
        animationEnabled: true,
        theme: "dark1",
        title: {
            text: "Sprint Burndown"
        },
        axisX: {
            valueFormatString: "D MMM"
        },
        axisY: {
            title: "No. Tasks"
        },
        toolTip: {
            shared: true
        },
        data: [{
            name: "Actual",
            type: "line",
            showInLegend: true,
            xValueFormatString: "DD MMM YYYY",
            lineThickness: 5,
            color: "yellow",
            dataPoints: [],
        }, {
            name: "Ideal",
            type: "line",
            showInLegend: true,
            lineThickness: 5,
            color: "silver",
            dataPoints: [],
        }]
    }

    protected chart: any;

    constructor(
        protected readonly sprintApi: SprintApiService,
        protected readonly projectMemberApi: ProjectMemberApiService,
        protected readonly backlogItemApi: BacklogItemApiService,
        protected readonly storage: StorageService,
    ) { }

    async ngAfterViewInit(): Promise<void> {
        this.chart = new CanvasJS.Chart("chartContainer", this.chartOptions);
        await this.loadAndDisplayCurrentSprint();
    }

    private async loadAndDisplayCurrentSprint(): Promise<void> {
        const projectId: number = this.storage.getValueFromStorage(StorageKey.PROJECT_ID);
        const sprints = await firstValueFrom(this.sprintApi.getCurrentAndFutureSprints(projectId));
        if (sprints.length == 0) {
            this.currentSprint = null;
        } else {
            this.currentSprint = undefined;
            this.loadSprintInfo(this.toSimpleSprint(sprints[0]));
        }
    }

    private async loadSprintInfo(sprint: SimpleSprint): Promise<void> {
        const [nextSprint, previousSprint] = (await Promise.all([
            firstValueFrom(this.sprintApi.getSprintsAfterSprint(sprint.sprintId, Pageable.of(0, 1, "startDate", "ASC"))),
            firstValueFrom(this.sprintApi.getSprintsBeforeSprint(sprint.sprintId, Pageable.of(0, 1, "startDate", "DESC"))),
        ])).map(page => page.numberOfElements > 0 ? this.toSimpleSprint(page.content[0]) : null);
        this.currentSprint = sprint;
        this.nextSprint = (nextSprint && nextSprint.startDate <= new Date()) ? nextSprint : null;
        this.previousSprint = previousSprint;

        this.updateBuckets(sprint);
    }

    private async updateBuckets(sprint: SimpleSprint): Promise<void> {
        const itemsResponse = await firstValueFrom(this.backlogItemApi.getAllBySprintId(sprint.sprintId));
        const allTasksInSprintCount = itemsResponse.length;

        const doneItems = itemsResponse
            .filter(item => item.status === BacklogItemStatus.DONE && item.taskFinishDate)
            .map(item => {
                item.taskFinishDate && (item.taskFinishDate = new Date(item.taskFinishDate));
                return item;
            })

        const [startDate, endDate] = [sprint.startDate, sprint.endDate];
        const [startTime, endTime] = [startDate.getTime(), endDate.getTime()];

        const dayLengthMs = 1000 * 60 * 60 * 24;
        const sprintTimespan = Math.floor((endTime - startTime) / dayLengthMs)

        const buckets = Array.from({ length: sprintTimespan + 1 }).map((_, i) => ({
            remainingTasks: allTasksInSprintCount,
            date: new Date(startTime + i * dayLengthMs),
        }));

        doneItems.forEach(item => {
            buckets.filter(bucket => bucket.date >= item.taskFinishDate)
                .forEach(bucket => bucket.remainingTasks--);
        });

        const theNextDayTime = new Date().getTime() + dayLengthMs;
        const completedAndCurrentBuckets = buckets
            .filter(bucket => bucket.date.getTime() <= theNextDayTime);

        const idealBoundaries = [
            { remainingTasks: allTasksInSprintCount, date: startDate, },
            { remainingTasks: 0, date: endDate, },
        ];

        this.updateDatapoints(completedAndCurrentBuckets, idealBoundaries);
    }

    private updateDatapoints(actual: Bucket[], ideal: Bucket[]) {
        const [actualPoints, idealPoints] = [actual, ideal].map(buckets =>
            buckets.map(bucket => {
                return { x: bucket.date, y: bucket.remainingTasks, };
            })
        );
        this.chart.options.data[0].dataPoints = actualPoints;
        this.chart.options.data[1].dataPoints = idealPoints;
        this.chart.render();
    }

    protected async switchDisplayedSprint(forward: boolean): Promise<void> {
        if (forward && this.nextSprint) {
            this.currentSprint = undefined;
            await this.loadSprintInfo(this.nextSprint);
        } else if (!forward && this.previousSprint) {
            this.currentSprint = undefined;
            await this.loadSprintInfo(this.previousSprint);
        }
    }

    private toSimpleSprint(sprintResponse: SprintResponse): SimpleSprint {
        return {
            sprintId: sprintResponse.sprintId,
            projectId: sprintResponse.projectId,
            sprintName: sprintResponse.sprintName,
            sprintDescription: sprintResponse.sprintDescription,
            startDate: new Date(sprintResponse.startDate),
            endDate: new Date(sprintResponse.endDate),
        };
    }

    protected formatDate(date: Date): string {
        return date.toISOString().split('T')[0].replaceAll("-", "/");
    }

}
