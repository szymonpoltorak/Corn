import { AfterViewInit, Component, Input, OnDestroy, ViewChild } from '@angular/core';
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
import { catchError, merge, Observable, of, startWith, Subject, switchMap, take, takeUntil } from "rxjs";
import { NgClass } from "@angular/common";
import { map } from "rxjs/operators";
import { BacklogItemService } from "@core/services/boards/backlog/backlog-item/backlog-item.service";
import { BacklogItemType } from "@core/enum/BacklogItemType";
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
import { StorageService } from "@core/services/storage.service";

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
        CdkDragPlaceholder
    ],
    templateUrl: './backlog-item-table.component.html',
    styleUrl: './backlog-item-table.component.scss',
    providers: [provideIcons({ bootstrapBugFill, featherBook, matTask, octContainer, matDelete })],
})
export class BacklogItemTableComponent implements AfterViewInit, OnDestroy{

    constructor(private backlogItemService: BacklogItemService,
                private backlogComponent: BacklogComponent,
                private storage: StorageService) {
    }

    @Input() sprintId: number = 0;
    @Input() sprintIds: string[] = [];

    dataToDisplay: BacklogItem[] = [];


    displayedColumns = ['title', 'description', 'status', 'type', 'assignee'];

    @ViewChild(MatSort) sort!: MatSort;
    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatTable) table!: MatTable<BacklogItem>;

    destroy$: Subject<void> = new Subject();

    resultsLength: number = 0;
    hoveredRow: BacklogItem | null = null;
    isLoading: boolean = true;

    deleteItem(item: BacklogItem): void {
        this.backlogItemService.deleteBacklogItem(item).pipe(take(1)).subscribe((deletedItem: BacklogItem) => {
            this.dataToDisplay = this.dataToDisplay.filter((i) => i !== item);
            this.resultsLength -= 1;
        });
    }

    protected readonly BacklogItemType = BacklogItemType;

    ngAfterViewInit(): void {
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

        if(this.sprintId === -1) {
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
                takeUntil(this.destroy$)
            ).subscribe(data => {
            this.dataToDisplay = data;

        })
    }

    updateBacklogItem(item: BacklogItem): void {
        this.backlogItemService.updateBacklogItem(item).pipe(take(1)).subscribe((newItem) => {
            this.dataToDisplay[this.dataToDisplay.indexOf(item)] = newItem;
        })
    }

    drop(event: CdkDragDrop<BacklogItem[]>) {

        if (event.previousContainer === event.container) {
            moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
        } else {
            transferArrayItem(event.previousContainer.data, event.container.data, event.previousIndex, event.currentIndex);

            const previousTable = this.backlogComponent.findBacklogItemTableById(event.previousContainer.id);
            if(previousTable) {
                previousTable.table.renderRows();
            }
            event.container.data[event.currentIndex].sprintId = this.sprintId;
            this.updateBacklogItem(event.container.data[event.currentIndex]);
        }

        this.table.renderRows();
    }

    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}
