import { AfterViewInit, Component, ViewChild, ViewEncapsulation } from '@angular/core';
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
    MatTable, MatTableDataSource
} from "@angular/material/table";
import { BacklogItem } from '@core/interfaces/boards/backlog.item';
import { NgIcon, provideIcons } from "@ng-icons/core";
import { matTask } from "@ng-icons/material-icons/baseline";
import { BacklogItemStatus } from "@core/enum/BacklogItemStatus";
import { BacklogItemType } from "@core/enum/BacklogItemType";
import { User } from "@core/interfaces/boards/user";
import { MatFormField, MatLabel, MatOption, MatSelect } from "@angular/material/select";
import { NgClass, NgForOf } from "@angular/common";
import { UserAvatarComponent } from "@pages/utils/user-avatar/user-avatar.component";
import { bootstrapBugFill } from "@ng-icons/bootstrap-icons";
import { MatTooltip } from "@angular/material/tooltip";
import { featherBook } from "@ng-icons/feather-icons";
import { octContainer } from "@ng-icons/octicons";
import { MatSort, MatSortHeader } from "@angular/material/sort";
import { MatButton, MatFabButton } from "@angular/material/button";
import { MatSnackBar } from "@angular/material/snack-bar";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatInput } from "@angular/material/input";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatDialog } from "@angular/material/dialog";
import { BacklogFormComponent } from "@pages/boards/backlog/backlog-form/backlog-form.component";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import { MatPaginator } from "@angular/material/paginator";
import { catchError, merge, of, startWith, switchMap, take } from "rxjs";
import { BacklogItemService } from "@core/services/boards/backlog/backlog-item.service";
import { map } from "rxjs/operators";
import { HttpClient } from "@angular/common/http";
import { environment } from "@environments/environment";
import { ApiUrl } from "@core/enum/api-url";

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
        MatPaginator
    ],
    templateUrl: './backlog.component.html',
    styleUrl: './backlog.component.scss',
    providers: [provideIcons({ bootstrapBugFill, featherBook, matTask, octContainer })],
    encapsulation: ViewEncapsulation.None
})
export class BacklogComponent implements AfterViewInit {

    constructor(private snackBar: MatSnackBar,
                public dialog: MatDialog,
                private backlogItemService: BacklogItemService,
                private http: HttpClient) {
    }

    @ViewChild(MatSort) sort!: MatSort;
    @ViewChild(MatPaginator) paginator!: MatPaginator;

    resultsLength: number = 0;
    ngAfterViewInit(): void {
        // this.dataSource.sort = this.sort;
        // this.dataSource.sortData = (data: BacklogItem[], sort: MatSort) => {
        //     if (!sort.active || sort.direction === '') {
        //         return data;
        //     }
        //
        //     return data.sort((a, b) => {
        //         let comparatorResult = 0;
        //         switch (sort.active) {
        //             case 'title':
        //                 comparatorResult = a.title.localeCompare(b.title);
        //                 break;
        //             case 'description':
        //                 comparatorResult = a.description.localeCompare(b.description);
        //                 break;
        //             case 'status':
        //                 comparatorResult = a.status.localeCompare(b.status);
        //                 break;
        //             case 'type':
        //                 comparatorResult = a.type - b.type;
        //                 break;
        //             case 'assignee':
        //                 comparatorResult = a.assignee.name.localeCompare(b.assignee.name);
        //                 if (comparatorResult === 0) {
        //                     comparatorResult = a.assignee.surname.localeCompare(b.assignee.surname);
        //                 }
        //                 break;
        //             default:
        //                 comparatorResult = a.id - b.id;
        //                 break;
        //         }
        //
        //         return comparatorResult * (sort.direction === 'asc' ? 1 : -1);
        //     })
        // }

        this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

        merge(this.sort.sortChange, this.paginator.page)
            .pipe(
                startWith({}),
                switchMap(() => {
                    this.isLoading = true;
                    return this.backlogItemService.getAllByProjectId(1)
                        .pipe(catchError(() => of(null)));
                }),
                map(data => {
                    this.isLoading = false;

                    console.log(data);

                    if(!data) {
                        return [];
                    }

                    this.resultsLength = data.length;
                    return data;
                })
            )
            .subscribe(data => {this.dataToDisplay = data});
    }


    isLoading: boolean = true;


    //only for example purposes
    statuses: BacklogItemStatus[] = [
        BacklogItemStatus.TODO,
        BacklogItemStatus.IN_PROGRESS,
        BacklogItemStatus.DONE
    ];

    users: User[] = [
        { userId: 0, name: 'John', surname: 'Doe' },
        { userId: 1, name: 'Szymon', surname: 'Kowalski' },
        { userId: 2, name: 'Andrzej', surname: 'Switch' }
    ];

    dataToDisplay: BacklogItem[] = [
        {
            id: 0,
            title: 'Create toolbar',
            'description': 'Create a toolbar for user',
            status: BacklogItemStatus.TODO,
            type: BacklogItemType.BUG,
            assignee: this.users[0]
        },
        {
            id: 1,
            title: 'Keycloak does not work',
            'description': 'blabla',
            status: BacklogItemStatus.TODO,
            type: BacklogItemType.TASK,
            assignee: this.users[1]
        },
        {
            id: 2,
            title: 'Add logout to app',
            'description': 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',
            status: BacklogItemStatus.TODO,
            type: BacklogItemType.EPIC,
            assignee: this.users[2]
        },
        {
            id: 3,
            title: 'Improve dockerfile',
            'description': 'Papope',
            status: BacklogItemStatus.IN_PROGRESS,
            type: BacklogItemType.STORY,
            assignee: this.users[0]
        },
        {
            id: 4,
            title: 'Fix content policy',
            'description': 'AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA',
            status: BacklogItemStatus.IN_PROGRESS,
            type: BacklogItemType.TASK,
            assignee: this.users[2]
        },
        {
            id: 5,
            title: 'Create folder structure',
            'description': 'idk',
            status: BacklogItemStatus.IN_PROGRESS,
            type: BacklogItemType.BUG,
            assignee: this.users[0]
        },
        {
            id: 6,
            title: 'Fix equals',
            'description': 'example description',
            status: BacklogItemStatus.DONE,
            type: BacklogItemType.EPIC,
            assignee: this.users[1]
        },
        {
            id: 7,
            title: 'Idk do something',
            'description': 'hello',
            status: BacklogItemStatus.DONE,
            type: BacklogItemType.STORY,
            assignee: this.users[2]
        },
        {
            id: 8,
            title: 'Hello World',
            'description': 'Hello World!\\n',
            status: BacklogItemStatus.DONE,
            type: BacklogItemType.BUG,
            assignee: this.users[0]
        }
    ];

    // dataSource = new MatTableDataSource(this.dataToDisplay);
    displayedColumns = ['title', 'description', 'status', 'type', 'assignee'];

    getStatusClass(status: BacklogItemStatus): string {
        return status.replace(' ', '_');
    }

    showItemForm(): void {
        const dialogRef = this.dialog.open(BacklogFormComponent, {
            width: '500px',
            enterAnimationDuration: '300ms',
            exitAnimationDuration: '100ms',
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.dataToDisplay.push({
                    id: this.dataToDisplay.length,
                    title: result.title,
                    description: result.description,
                    status: BacklogItemStatus.TODO,
                    type: result.type,
                    assignee: this.users.find(user => user.userId === result.assignee)!
                });
                // this.dataSource.data = this.dataToDisplay;
            }
        })
    }

    updateStatus(item: BacklogItem): void {
        //only for example purposes
        this.snackBar.open('Setting status: ' + item.status + ' for item: ' + item.title, 'Close');

        //TODO real interaction with backend
    }


    protected readonly BacklogItemType = BacklogItemType;
}
