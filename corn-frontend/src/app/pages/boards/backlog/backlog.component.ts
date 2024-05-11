import { Component, EventEmitter, OnInit, QueryList, ViewChildren, ViewEncapsulation } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatDialog } from "@angular/material/dialog";
import { BacklogFormComponent } from "@pages/boards/backlog/backlog-form/backlog-form.component";
import { take } from "rxjs";
import { BacklogItemService } from "@core/services/boards/backlog/backlog-item/backlog-item.service";
import { Sprint } from "@interfaces/boards/backlog/sprint";
import { BacklogItemTableComponent } from "@pages/boards/backlog/backlog-item-table/backlog-item-table.component";
import {
    MatAccordion,
    MatExpansionPanel,
    MatExpansionPanelDescription,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
} from "@angular/material/expansion";
import { SprintService } from "@core/services/boards/backlog/sprint/sprint.service";
import { DatePipe, NgForOf } from "@angular/common";
import { MatMenuModule } from "@angular/material/menu";
import { CdkContextMenuTrigger, CdkMenu, CdkMenuItem } from "@angular/cdk/menu";
import { MatRipple } from "@angular/material/core";
import { BacklogEditFormComponent } from "@pages/boards/backlog/backlog-edit-form/backlog-edit-form.component";
import { SprintEditData } from "@interfaces/boards/backlog/sprint-edit-data.interfaces";
import { DeleteDialogComponent } from "@pages/utils/delete-dialog/delete-dialog.component";
import { MatIcon } from "@angular/material/icon";
import { SprintRequest } from "@interfaces/boards/backlog/sprint-request.interfaces";
import { StorageService } from "@core/services/storage.service";
import { StorageKey } from "@core/enum/storage-key.enum";

@Component({
    selector: 'app-backlog',
    standalone: true,
    imports: [
        MatButton,
        MatAccordion,
        MatExpansionPanel,
        MatExpansionPanelTitle,
        MatExpansionPanelDescription,
        MatExpansionPanelHeader,
        NgForOf,
        BacklogItemTableComponent,
        DatePipe,
        MatMenuModule,
        CdkContextMenuTrigger,
        CdkMenu,
        CdkMenuItem,
        MatIcon,
        MatRipple,
    ],
    templateUrl: './backlog.component.html',
    styleUrl: './backlog.component.scss',
    encapsulation: ViewEncapsulation.None
})
export class BacklogComponent implements OnInit {

    @ViewChildren(BacklogItemTableComponent) backlogItemTableComponents!: QueryList<BacklogItemTableComponent>;
    sprintChangedEmitter: EventEmitter<number> = new EventEmitter<number>();
    sprints: Sprint[] = [];
    sprintIds: string[] = [];

    constructor(private dialog: MatDialog,
                private backlogItemService: BacklogItemService,
                private sprintService: SprintService,
                private localStorage: StorageService) {
    }

    ngOnInit(): void {
        this.sprintService
            .getCurrentAndFutureSprints()
            .pipe(take(1))
            .subscribe((sprints) => {
                this.sprints = sprints;
                this.sprintIds = sprints.map(sprint => sprint.sprintId.toString());
                this.sprintIds.push('-1');
            })
    }

    showItemForm(): void {
        const dialogRef = this.dialog.open(BacklogFormComponent, {
            enterAnimationDuration: '300ms',
            exitAnimationDuration: '100ms',
        });

        dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
            if (!result) {
                return;
            }
            this.backlogItemService.createNewBacklogItem(
                result.title,
                result.description,
                result.assignee.userId,
                result.sprintId,
                result.type
            ).pipe(take(1)).subscribe((newItem) => {
                const table: BacklogItemTableComponent | undefined = this.findBacklogItemTableById(newItem.sprintId.toString());

                if (table) {
                    table.fetchBacklogItems();
                }
            })
        })
    }

    sprintChanged(sprintId: number): void {
        this.sprintChangedEmitter.emit(sprintId);
    }

    findBacklogItemTableById(id: string): BacklogItemTableComponent | undefined {
        return this.backlogItemTableComponents.find(table => table.sprintId.toString() === id);
    }

    deleteSprint(sprintId: number): void {
        const dialogRef = this.dialog.open(DeleteDialogComponent, {
            enterAnimationDuration: '300ms',
            exitAnimationDuration: '100ms',
        });

        dialogRef.afterClosed().pipe(take(1)).subscribe((result) => {
            if (result === false) {
                return;
            }
            this.sprintService
                .deleteSprint(sprintId)
                .pipe(take(1))
                .subscribe(() => {
                    this.sprints = this.sprints.filter(sprint => sprint.sprintId !== sprintId);
                });
        });
    }

    editSprint(sprint: Sprint): void {
        const dialogRef = this.dialog.open(BacklogEditFormComponent, {
            enterAnimationDuration: '300ms',
            exitAnimationDuration: '100ms',
            data: sprint
        });

        dialogRef.afterClosed().pipe(take(1)).subscribe((newSprintData: SprintEditData) => {
            if (!newSprintData) {
                return;
            }
            if (newSprintData.sprintName !== sprint.sprintName) {
                sprint.sprintName = this.updateSprintsName(newSprintData, sprint);
            }
            if (newSprintData.goal !== sprint.sprintDescription) {
                sprint.sprintDescription = this.updateSprintsGoal(newSprintData, sprint);
            }
            if (newSprintData.startDate !== sprint.startDate) {
                sprint.startDate = this.updateSprintStartDate(newSprintData, sprint);
            }
            if (newSprintData.endDate !== sprint.endDate) {
                sprint.endDate = this.updateSprintEndDate(newSprintData, sprint);
            }
            this.sprints = this.sprints.map(s => s.sprintId === sprint.sprintId ? sprint : s);
        });
    }

    createNewSprint(): void {
        const newSprint: Sprint = {
            sprintId: -1,
            projectId: -1,
            sprintName: `Sprint ${ this.sprints.length + 1 }`,
            sprintDescription: '',
            startDate: '',
            endDate: ''
        }
        const dialogRef = this.dialog.open(BacklogEditFormComponent, {
            enterAnimationDuration: '300ms',
            exitAnimationDuration: '100ms',
            data: newSprint
        });

        dialogRef.afterClosed().pipe(take(1)).subscribe((result: SprintEditData) => {
            if (!result) {
                return;
            }
            const request: SprintRequest = {
                projectId: this.localStorage.getValueFromStorage(StorageKey.PROJECT_ID),
                sprintName: result.sprintName,
                description: result.goal,
                startDate: result.startDate,
                endDate: result.endDate
            }
            this.sprintService
                .createSprint(request)
                .pipe(take(1))
                .subscribe((sprint: Sprint) => {
                    this.sprints.push(sprint);
                });
        });
    }

    private updateSprintsName(newSprintData: SprintEditData, sprint: Sprint): string {
        this.sprintService
            .editSprintName(newSprintData.sprintName, sprint.sprintId)
            .pipe(take(1))
            .subscribe();
        return newSprintData.sprintName;
    }

    private updateSprintsGoal(newSprintData: SprintEditData, sprint: Sprint): string {
        this.sprintService
            .editSprintDescription(newSprintData.goal, sprint.sprintId)
            .pipe(take(1))
            .subscribe();
        return newSprintData.goal;
    }

    private updateSprintStartDate(newSprintData: SprintEditData, sprint: Sprint): string {
        this.sprintService
            .editSprintStartDate(newSprintData.startDate, sprint.sprintId)
            .pipe(take(1))
            .subscribe();
        return newSprintData.startDate;
    }

    private updateSprintEndDate(newSprintData: SprintEditData, sprint: Sprint): string {
        this.sprintService
            .editSprintEndDate(newSprintData.endDate, sprint.sprintId)
            .pipe(take(1))
            .subscribe();
        return newSprintData.endDate;
    }
}
