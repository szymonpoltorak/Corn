import { AfterViewInit, Component, Input, OnDestroy, ViewChild } from '@angular/core';
import { BacklogItem } from "@interfaces/boards/backlog/backlog.item";
import { MatSort, MatSortHeader } from "@angular/material/sort";
import {
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
    MatTable
} from "@angular/material/table";
import { MatOption, MatSelect } from "@angular/material/select";
import { BacklogItemStatus } from "@core/enum/BacklogItemStatus";
import { MatPaginator } from "@angular/material/paginator";
import { catchError, merge, of, startWith, Subject, switchMap, take, takeUntil } from "rxjs";
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
        MatSortHeader
    ],
    templateUrl: './backlog-item-table.component.html',
    styleUrl: './backlog-item-table.component.scss',
    providers: [provideIcons({ bootstrapBugFill, featherBook, matTask, octContainer, matDelete })],
})
export class BacklogItemTableComponent implements AfterViewInit, OnDestroy {

    constructor(private backlogItemService: BacklogItemService) {
    }

    @Input() sprintId: number = 0;

    dataToDisplay: BacklogItem[] = [];


    displayedColumns = ['title', 'description', 'status', 'type', 'assignee'];

    @ViewChild(MatSort) sort!: MatSort;
    @ViewChild(MatPaginator) paginator!: MatPaginator;

    destroy$: Subject<void> = new Subject();

    resultsLength: number = 0;
    hoveredRow: BacklogItem | null = null;
    isLoading: boolean = true;

    statuses: BacklogItemStatus[] = [
        BacklogItemStatus.TODO,
        BacklogItemStatus.IN_PROGRESS,
        BacklogItemStatus.DONE
    ];

    getStatusClass(status: BacklogItemStatus): string {
        return status.replace(' ', '_');
    }

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
        this.backlogItemService.getAllBySprintId(
            this.sprintId,
            this.paginator.pageIndex,
            active,
            this.sort.direction.toUpperCase())
            .pipe(
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

    updateStatus(item: BacklogItem): void {
        this.backlogItemService.updateBacklogItem(item).pipe(take(1)).subscribe((newItem) => {
            this.dataToDisplay[this.dataToDisplay.indexOf(item)] = newItem;
        })
    }

    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}
