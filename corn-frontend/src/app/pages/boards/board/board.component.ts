import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { SlicesModelService } from '@core/services/boards/board/slice/slices_model.service';
import { SliceService } from '@core/services/boards/board/slice/slice.service';
import { SliceComponent } from './slice/slice.component';
import { BoardModelService } from '@core/services/boards/board/model.service';
import { MatIconModule } from '@angular/material/icon';
import { Task } from '@core/interfaces/boards/board/task.interface';
import { Assignee } from '@core/interfaces/boards/board/assignee.interface';
import { TaskGrouping } from '@core/enum/boards/board/TaskGrouping';
import { ColumnSetLayout } from './layout/column_set_layout.component';
import { GroupingMetadata } from '@core/types/board/boards/GroupingMetadata';
import { TaskGrouper } from '@core/types/board/boards/TaskGrouper';
import { TaskChangedGroupEvent } from '@core/interfaces/boards/board/task_changed_group_event.interface';
import { TaskChangedColumnEvent } from '@core/interfaces/boards/board/task_changed_column_event.interface';
import { Hideable } from '@core/interfaces/boards/board/hideable.interface';
import { SprintApi } from '@core/services/api/v1/sprint/sprint-api.service';
import { StorageService } from '@core/services/storage.service';
import { SprintResponse } from '@core/services/api/v1/sprint/data/sprint-response.interface';
import { ProjectMemberApi } from '@core/services/api/v1/project/member/project-member-api.service';
import { BacklogItemApi } from '@core/services/api/v1/backlog/item/backlog-item-api.service';
import { BacklogItemStatus } from '@core/enum/BacklogItemStatus';
import { StorageKey } from '@core/enum/storage-key.enum';
import { firstValueFrom } from 'rxjs';
import {
    ProjectMemberInfoExtendedResponse
} from '@core/services/api/v1/project/member/data/project-member-info-extended-reponse.interface';
import { BacklogItemResponse } from '@core/services/api/v1/backlog/item/data/backlog-item-response.interface';
import { UsernameToAssigneeMapper } from '@core/types/board/boards/UsernameToAssigneeMapper';
import { SimpleSprint } from '@core/interfaces/boards/board/simple_sprint.interface';
import { Pageable } from '@core/services/api/utils/pageable.interface';

@Component({
    selector: 'app-board',
    standalone: true,
    imports: [
        CommonModule,
        MatInputModule,
        MatMenuModule,
        MatButtonModule,
        SliceComponent,
        MatIconModule,
        ColumnSetLayout,
    ],
    providers: [
        BoardModelService,
        SlicesModelService,
        SliceService,
    ],
    templateUrl: './board.component.html',
})
export class BoardComponent implements OnInit {

    protected currentSprint: SimpleSprint | null | undefined = undefined;
    protected previousSprint: SimpleSprint | null = null;
    protected nextSprint: SimpleSprint | null = null;
    protected filterString: string = "";
    protected taskGrouping: TaskGrouping = TaskGrouping.NONE;

    constructor(
        protected readonly sprintApi: SprintApi,
        protected readonly projectMemberApi: ProjectMemberApi,
        protected readonly backlogItemApi: BacklogItemApi,
        protected readonly storage: StorageService,
        protected readonly modelService: BoardModelService,
        protected readonly slicesModelService: SlicesModelService<GroupingMetadata>,
    ) {
    }

    async ngOnInit(): Promise<void> {
        this.modelService.modelChangeHandler = () => {
            this.slicesModelService.rebuildSlices();
        }
        this.slicesModelService.groupChangedHandler = this.groupChangedHandler.bind(this);
        this.slicesModelService.columnChangedHandler = this.columnChangedHandler.bind(this);
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
        const [nextSprint, previousSprint] = [
            await firstValueFrom(this.sprintApi.getSprintsAfterSprint(sprint.sprintId, Pageable.of(0, 1, "startDate", "ASC"))),
            await firstValueFrom(this.sprintApi.getSprintsBeforeSprint(sprint.sprintId, Pageable.of(0, 1, "startDate", "DESC"))),
        ].map(page => page.numberOfElements > 0 ? this.toSimpleSprint(page.content[0]) : null);
        this.nextSprint = nextSprint;
        this.previousSprint = previousSprint;

        const members = await firstValueFrom(this.projectMemberApi.getAllProjectMembers(sprint.projectId));

        this.modelService.assignees = members.map(this.toAssignee);

        const usernameToAssigneesMap = this.modelService.assignees.reduce((acc: any, next) => {
            acc[next.associatedUsername] = next;
            return acc;
        }, {});
        const usernameToAssingeeMapper = (username: string) => usernameToAssigneesMap[username];

        const items = await firstValueFrom(this.backlogItemApi.getAllBySprintId(sprint.sprintId));

        this.updateModel(sprint, items, usernameToAssingeeMapper);
    }

    private async updateModel(sprint: SimpleSprint, items: BacklogItemResponse[], mapper: UsernameToAssigneeMapper): Promise<void> {
        this.modelService.todo = [];
        this.modelService.inprogress = [];
        this.modelService.done = [];

        items.forEach(item => {
            const task = this.toTask(item, mapper)
            if (item.status == BacklogItemStatus.TODO) {
                this.modelService.todo.push(task)
            } else if (item.status == BacklogItemStatus.IN_PROGRESS) {
                this.modelService.inprogress.push(task)
            } else if (item.status == BacklogItemStatus.DONE) {
                this.modelService.done.push(task)
            }
        })

        this.currentSprint = sprint;

        this.updateFilterString(this.filterString);
        this.updateTaskGrouping(this.taskGrouping);
    }

    protected async switchDisplayedSprint(forward: boolean): Promise<void> {
        if(forward && this.nextSprint) {
            this.currentSprint = undefined;
            await this.loadSprintInfo(this.nextSprint);
        } else if(!forward && this.previousSprint) {
            this.currentSprint = undefined;
            await this.loadSprintInfo(this.previousSprint);
        }
    }

    protected isDisplayedSprintEditable(): boolean {
        const currentDate = new Date();
        return currentDate >= this.currentSprint!.startDate && currentDate <= this.currentSprint!.endDate;
    }

    private columnChangedHandler(event: TaskChangedColumnEvent): void {
        if(!this.isDisplayedSprintEditable()) {
            return;
        }
        this.modelService.moveTaskToArray(event.task,
            event.sourceColumn, event.sourceColumnIndex,
            event.destinationColumn, event.destinationColumnIndex
        );
        const status = this.mapColumnToStatus(event.destinationColumn);
        setTimeout(() => firstValueFrom(
            this.backlogItemApi.partialUpdate(event.task.associatedBacklogItemId, {
                itemStatus: status,
            })
        ), 500);
    }

    protected assigneeChangedHandler(event: TaskChangedGroupEvent<Assignee>): void {
        if(!this.isDisplayedSprintEditable()) {
            return;
        }
        this.modelService.setAssigneeForTask(event.task, event.destinationGroupMetadata);
        this.backlogItemApi.partialUpdate(event.task.associatedBacklogItemId, {
            projectMemberId: event.task.assignee.associatedProjectMemberId,
        }).subscribe((_) => { });
    }

    protected groupChangedHandler(event: TaskChangedGroupEvent<GroupingMetadata>): void {
        if(!this.isDisplayedSprintEditable()) {
            return;
        }
        if (this.taskGrouping == TaskGrouping.BY_ASSIGNEE &&
            event.sourceGroupMetadata != event.destinationGroupMetadata &&
            event.destinationGroupMetadata != null &&
            event.destinationGroupMetadata != undefined
        ) {
            this.assigneeChangedHandler(event as TaskChangedGroupEvent<Assignee>);
            this.modelService.setAssigneeForTask(event.task, event.destinationGroupMetadata);
        }
    }

    protected updateFilterString(filterString: string): void {
        filterString = filterString.toLowerCase();
        this.filterString = filterString;
        const stringPredicate = (s: string) => s.toLowerCase().includes(filterString);
        this.slicesModelService.filter = (t: Task) => {
            return stringPredicate(t.content)
                || stringPredicate(t.assignee.firstName)
                || stringPredicate(t.assignee.firstName + " " + t.assignee.familyName)
                || stringPredicate(t.assignee.familyName)
                || stringPredicate(t.associatedBacklogItemId + "")
                || stringPredicate(t.taskTag);
        };
        this.slicesModelService.rebuildSlices();
    }

    protected updateTaskGrouping(taskGrouping: TaskGrouping): void {
        this.taskGrouping = taskGrouping;
        this.slicesModelService.grouper = this.GROUPERS[taskGrouping];
        this.slicesModelService.rebuildSlices();
    }

    protected toggleHidden(element: Hideable): void {
        element.hidden = !element.hidden;
    }

    protected isHidden(element: Hideable): boolean {
        return element.hidden;
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

    private toAssignee(member: ProjectMemberInfoExtendedResponse): Assignee {
        return {
            associatedUserId: member.user.userId,
            associatedUsername: member.user.username,
            associatedProjectMemberId: member.projectMemberId,
            firstName: member.user.name,
            familyName: member.user.surname,
            avatarUrl: "/assets/assignee-avatars/alice.png",
        };
    }

    private mapColumnToStatus(column: Task[]): BacklogItemStatus {
        if (column === this.modelService.todo) {
            return BacklogItemStatus.TODO;
        } else if (column === this.modelService.inprogress) {
            return BacklogItemStatus.IN_PROGRESS;
        } else if (column === this.modelService.done) {
            return BacklogItemStatus.DONE;
        }
        throw new Error("Unknown column");
    }

    private toTask(item: BacklogItemResponse, mapper: UsernameToAssigneeMapper): Task {
        return {
            associatedBacklogItemId: item.backlogItemId,
            taskTag: item.itemType + "-" + item.backlogItemId,
            content: item.title,
            assignee: mapper(item.assignee.username),
        };
    }

    protected formatDate(date: Date): string {
        return date.toISOString().split('T')[0].replaceAll("-", "/");
    }

    protected readonly TaskGroupingEnum: typeof TaskGrouping = TaskGrouping;
    protected readonly TASK_GROUPINGS: TaskGrouping[] = Object.values(TaskGrouping);
    protected readonly GROUPERS: { [key in TaskGrouping]: TaskGrouper<GroupingMetadata>; } = {
        [TaskGrouping.NONE]: (ungrouped: Task[]) => {
            return [{ metadata: null, group: ungrouped }]
        },
        [TaskGrouping.BY_ASSIGNEE]: (ungrouped: Task[]) => {
            return this.modelService.assignees.map((a: Assignee) => {
                return {
                    metadata: a,
                    group: ungrouped.filter((t: Task) => {
                        return t.assignee.firstName == a.firstName
                            && t.assignee.familyName == a.familyName
                    })
                };
            });
        },
    };

}
