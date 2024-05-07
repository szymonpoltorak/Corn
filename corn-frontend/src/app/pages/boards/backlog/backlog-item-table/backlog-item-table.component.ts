import { AfterViewInit, Component, EventEmitter, Input, OnDestroy, Output, ViewChild } from '@angular/core';
import { BacklogItem } from "@interfaces/boards/backlog/backlog.item";
import { MatSort, MatSortHeader } from "@angular/material/sort";
import {
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    MatTable
} from "@angular/material/table";
import { MatOption, MatSelect } from "@angular/material/select";
import { MatPaginator } from "@angular/material/paginator";
import { catchError, firstValueFrom, merge, Observable, of, startWith, Subject, switchMap, take, takeUntil } from "rxjs";
import { NgClass } from "@angular/common";
import { map } from "rxjs/operators";
import { BacklogItemService } from "@core/services/boards/backlog/backlog-item/backlog-item.service";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { bootstrapBugFill } from "@ng-icons/bootstrap-icons";
import { featherBook } from "@ng-icons/feather-icons";
import { matDelete, matTask } from "@ng-icons/material-icons/baseline";
import { octContainer } from "@ng-icons/octicons";
import { UserAvatarComponent } from "@pages/utils/user-avatar/user-avatar.component";
import { MatFabButton } from "@angular/material/button";
import { MatTooltip } from "@angular/material/tooltip";
import { BacklogItemList } from "@interfaces/boards/backlog/backlog.item.list";
import {
    CdkDrag,
    CdkDragDrop,
    CdkDragPlaceholder,
    CdkDragPreview,
    CdkDropList,
    moveItemInArray,
    transferArrayItem
} from "@angular/cdk/drag-drop";
import { BacklogComponent } from "@pages/boards/backlog/backlog.component";
import { StatusSelectComponent } from "@pages/boards/backlog/backlog-item-table/status-select/status-select.component";
import { BacklogTypeComponent } from "@pages/boards/backlog/backlog-item-table/backlog-type/backlog-type.component";
import { BacklogDragComponent } from "@pages/boards/backlog/backlog-item-table/backlog-drag/backlog-drag.component";
import { MatDialog, MatDialogRef } from "@angular/material/dialog";
import { BacklogItemDetailsComponent } from "@pages/boards/backlog/backlog-item-details/backlog-item-details.component";
import { DeleteDialogComponent } from "@pages/utils/delete-dialog/delete-dialog.component";
import { ChangeAssigneeMenuComponent } from '@pages/utils/change_assignee_menu/change_assignee_menu.component';
import { Assignee } from '@core/interfaces/boards/board/assignee.interface';
import { TaskChangedGroupEvent } from '@core/interfaces/boards/board/task_changed_group_event.interface';
import { BacklogItemApiService } from '@core/services/api/v1/backlog/item/backlog-item-api.service';
import { Task } from '@core/interfaces/boards/board/task.interface';
import { ProjectMemberApiService } from '@core/services/api/v1/project/member/project-member-api.service';
import { StorageService } from '@core/services/storage.service';
import { StorageKey } from '@core/enum/storage-key.enum';
import { ProjectMemberInfoExtendedResponse } from '@core/services/api/v1/project/member/data/project-member-info-extended-reponse.interface';
import { User } from '@core/interfaces/boards/user';
import { ChangeItemTypeMenuComponent } from '@pages/utils/change_item_type_menu/change_item_type_menu.component';
import { BacklogItemType } from '@core/enum/BacklogItemType';

@Component({
    selector: 'app-backlog-item-table',
    standalone: true,
    imports: [
        MatSort,
        MatColumnDef,
        MatTable,
        MatHeaderCellDef,
        MatSelect,
        MatOption,
        NgClass,
        NgIcon,
        UserAvatarComponent,
        MatCell,
        MatHeaderCell,
        MatFabButton,
        MatPaginator,
        MatCellDef,
        MatTooltip,
        MatHeaderRow,
        MatRow,
        MatHeaderRowDef,
        MatRowDef,
        MatSortHeader,
        CdkDropList,
        CdkDrag,
        CdkDragPreview,
        StatusSelectComponent,
        BacklogTypeComponent,
        BacklogDragComponent,
        CdkDragPlaceholder,
        ChangeAssigneeMenuComponent,
        ChangeItemTypeMenuComponent,
    ],
    templateUrl: './backlog-item-table.component.html',
    styleUrl: './backlog-item-table.component.scss',
    providers: [provideIcons({bootstrapBugFill, featherBook, matTask, octContainer, matDelete})],
})
export class BacklogItemTableComponent implements AfterViewInit, OnDestroy {

    @Input() sprintId: number = 0;
    @Input() sprintIds: string[] = [];
    @Input() inputSprintChanged!: EventEmitter<number>
    @ViewChild(MatSort) sort!: MatSort;
    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatTable) table!: MatTable<BacklogItem>;
    @Output() outputSprintChanged: EventEmitter<number> = new EventEmitter<number>

    dataToDisplay: BacklogItem[] = [];
    displayedColumns: string[] = ['title', 'description', 'status', 'type', 'assignee'];
    destroy$: Subject<void> = new Subject<void>();
    resultsLength: number = 0;
    hoveredRow: BacklogItem | null = null;
    isLoading: boolean = true;
    projectMembers: Assignee[] = [];


    constructor(private backlogItemService: BacklogItemService,
                private backlogComponent: BacklogComponent,
                private dialog: MatDialog,
                private projectMemberApi: ProjectMemberApiService,
                private backlogItemApi: BacklogItemApiService,
                private storage: StorageService) {
    }

    deleteItem(item: BacklogItem): void {
        const dialogRef = this.dialog.open(DeleteDialogComponent, {
            enterAnimationDuration: '100ms',
            exitAnimationDuration: '100ms'
        })

        dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
            if (!result) {
                return;
            }

            this.backlogItemService.deleteBacklogItem(item).pipe(take(1)).subscribe((deletedItem: BacklogItem) => {
                this.dataToDisplay = this.dataToDisplay.filter((i) => i !== item);
                this.resultsLength -= 1;
            });
        })


    }

    ngAfterViewInit(): void {
        this.inputSprintChanged.pipe(takeUntil(this.destroy$)).subscribe((sprintId: number) => {
            if (sprintId == this.sprintId) {
                this.fetchBacklogItems();
            }
        });

        this.sort.sortChange.pipe(takeUntil(this.destroy$)).subscribe(
            () => (this.paginator.pageIndex = 0));

        merge(this.sort.sortChange, this.paginator.page)
            .pipe(
                startWith({}),
                switchMap(() => {
                    this.fetchBacklogItems();
                    return of(null);
                })
            ).pipe(takeUntil(this.destroy$)).subscribe();
    }

    fetchBacklogItems(): void {
        this.isLoading = true;
        let active: string = this.sort.active === 'type' ? 'itemType' : this.sort.active;
        let source: Observable<BacklogItemList>;
        this.fetchProjectMembers();

        if (this.sprintId === -1) {
            source = this.backlogItemService.getAllWithoutSprint(this.paginator.pageIndex, active, this.sort.direction.toUpperCase());
        } else {
            source = this.backlogItemService.getAllBySprintId(this.sprintId, this.paginator.pageIndex, active, this.sort.direction.toUpperCase());
        }

        source.pipe(
            catchError(() => of(null)),
            map(data => {
                this.isLoading = false;

                if (!data) {
                    return [];
                }

                this.resultsLength = data.totalNumber;
                return data.backlogItemResponseList;
            }),
            take(1)
        ).subscribe(data => {
            this.dataToDisplay = data;
        })
    }

    updateBacklogItem(item: BacklogItem): void {
        this.backlogItemService.updateBacklogItem(item).pipe(take(1)).subscribe((newItem) => {
            if (newItem.sprintId == this.sprintId) {
                let index: number = this.dataToDisplay.findIndex(item => item.backlogItemId == newItem.backlogItemId);
                this.dataToDisplay[index] = newItem;
                this.table.renderRows();
            } else {
                this.fetchBacklogItems();
                this.outputSprintChanged.emit(newItem.sprintId);
            }
        })
    }

    drop(event: CdkDragDrop<BacklogItem[]>): void {

        if (event.previousContainer === event.container) {
            moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
        } else {
            transferArrayItem(event.previousContainer.data, event.container.data, event.previousIndex, event.currentIndex);

            const previousTable = this.backlogComponent.findBacklogItemTableById(event.previousContainer.id);
            if (previousTable) {
                previousTable.table.renderRows();
            }
            event.container.data[event.currentIndex].sprintId = this.sprintId;
            this.updateBacklogItem(event.container.data[event.currentIndex]);
        }

        this.table.renderRows();
    }

    openDetails(item: BacklogItem): void {
        const dialogRef: MatDialogRef<BacklogItemDetailsComponent> = this.dialog.open(BacklogItemDetailsComponent, {
            width: '1000px',
            enterAnimationDuration: '300ms',
            exitAnimationDuration: '100ms',
            data: item
        });

        dialogRef.afterClosed().pipe(take(1)).subscribe((result: BacklogItem) => {
            if (!result) {
                return;
            }

            this.updateBacklogItem(result);
        })
    }

    protected fetchProjectMembers(): void {
        const projectId: number = this.storage.getValueFromStorage(StorageKey.PROJECT_ID);
        this.projectMemberApi.getAllProjectMembers(projectId).subscribe(members => {
            this.projectMembers = members.map(this.toAssignee);
        });
    }

    protected getProjectMembers(): Assignee[] {
        return this.projectMembers;
    }

    protected assigneeChangedHandler(event: TaskChangedGroupEvent<Assignee | undefined>): void{
        const projectMemberResponse = this.projectMembers.filter(member =>
            member.associatedUserId === event.destinationGroupMetadata?.associatedUserId
        )[0];
        const projectMemberId = projectMemberResponse.associatedProjectMemberId;
        this.backlogItemApi.partialUpdate(event.task.associatedBacklogItemId, {
            projectMemberId: projectMemberId,
        }).subscribe((response) => {
            const item = this.dataToDisplay.filter(item => item.backlogItemId == response.backlogItemId)[0];
            item.assignee = this.assigneeToUser(event.destinationGroupMetadata!);
        });
    }

    protected itemTypeChangedHandler(event: { itemId: number, type: BacklogItemType }): void {
        this.backlogItemApi.partialUpdate(event.itemId, {
            itemType: event.type
        }).subscribe((response) => {
            const item = this.dataToDisplay.filter(item => item.backlogItemId == response.backlogItemId)[0];
            item.itemType = event.type;
        });
    }

    protected backlogItemToTask(backlogItem: BacklogItem): Task {
        return {
            associatedBacklogItemId: backlogItem.backlogItemId,
            taskTag: backlogItem.itemType + "-" + backlogItem.backlogItemId,
            content: backlogItem.title,
            assignee: !backlogItem.assignee ? undefined : {
                associatedUserId: backlogItem.assignee.userId,
                associatedUsername: backlogItem.assignee.username,
                associatedProjectMemberId: -1,
                firstName: backlogItem.assignee.name,
                familyName: backlogItem.assignee.surname,
                avatarUrl: '',
            },
            taskType: backlogItem.itemType,
        };
    }

    protected assigneeToUser(assignee: Assignee): User {
        return {
            userId: assignee.associatedUserId,
            name: assignee.firstName,
            surname: assignee.familyName,
            username: assignee.associatedUsername,
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

    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}
