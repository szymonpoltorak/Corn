import { AfterViewInit, Component, OnDestroy, ViewChild, ViewEncapsulation } from '@angular/core';
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
    MatTable,
} from "@angular/material/table";
import { BacklogItem } from '@interfaces/boards/backlog/backlog.item';
import { NgIcon, provideIcons } from "@ng-icons/core";
import { matDelete, matTask } from "@ng-icons/material-icons/baseline";
import { BacklogItemStatus } from "@core/enum/BacklogItemStatus";
import { BacklogItemType } from "@core/enum/BacklogItemType";
import { MatFormField, MatLabel, MatOption, MatSelect } from "@angular/material/select";
import { NgClass, NgForOf } from "@angular/common";
import { UserAvatarComponent } from "@pages/utils/user-avatar/user-avatar.component";
import { bootstrapBugFill } from "@ng-icons/bootstrap-icons";
import { MatTooltip } from "@angular/material/tooltip";
import { featherBook } from "@ng-icons/feather-icons";
import { octContainer } from "@ng-icons/octicons";
import { MatSort, MatSortHeader } from "@angular/material/sort";
import { MatButton, MatButtonModule, MatFabButton, MatIconButton } from "@angular/material/button";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatInput } from "@angular/material/input";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatDialog } from "@angular/material/dialog";
import { BacklogFormComponent } from "@pages/boards/backlog/backlog-form/backlog-form.component";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { MatPaginator } from "@angular/material/paginator";
import { catchError, merge, of, startWith, Subject, switchMap, take, takeUntil } from "rxjs";
import { BacklogItemService } from "@core/services/boards/backlog/backlog-item/backlog-item.service";
import { map } from "rxjs/operators";
import { MatIcon } from "@angular/material/icon";

@Component({
    selector: 'app-backlog',
    standalone: true,
    imports: [
        MatTable,
        MatColumnDef,
        MatHeaderCell,
        MatCell,
        MatCellDef,
        MatHeaderCellDef,
        MatHeaderRow,
        MatHeaderRowDef,
        MatRow,
        MatRowDef,
        NgIcon,
        MatSelect,
        MatOption,
        NgClass,
        NgForOf,
        UserAvatarComponent,
        MatTooltip,
        MatSortHeader,
        MatSort,
        MatButton,
        MatFabButton,
        FormsModule,
        ReactiveFormsModule,
        MatLabel,
        MatInput,
        MatFormField,
        MatFormFieldModule,
        MatProgressSpinner,
        MatPaginator,
        MatIcon,
        MatIconButton,
        MatButtonModule
    ],
    templateUrl: './backlog.component.html',
    styleUrl: './backlog.component.scss',
    providers: [provideIcons({ bootstrapBugFill, featherBook, matTask, octContainer, matDelete })],
    encapsulation: ViewEncapsulation.None
})
export class BacklogComponent implements AfterViewInit, OnDestroy {

    constructor(public dialog: MatDialog,
                private backlogItemService: BacklogItemService) {
    }

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

    dataToDisplay: BacklogItem[] = [];
    displayedColumns = ['title', 'description', 'status', 'type', 'assignee'];

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
        this.backlogItemService.getAllByProjectId(
            1,                  //TODO get real projectId from somewhere
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

    getStatusClass(status: BacklogItemStatus): string {
        return status.replace(' ', '_');
    }

    deleteItem(item: BacklogItem): void {
        this.backlogItemService.deleteBacklogItem(item).pipe(take(1)).subscribe((deletedItem: BacklogItem) => {
            this.dataToDisplay = this.dataToDisplay.filter((i) => i !== item);
            this.resultsLength -= 1;
        });
    }

    showItemForm(): void {
        const dialogRef = this.dialog.open(BacklogFormComponent, {
            width: '500px',
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
                1,  //TODO get real projectId from somewhere
                result.type
            ).pipe(take(1)).subscribe((newItem) => {
                this.fetchBacklogItems();
            })
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
