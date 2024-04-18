import { AfterViewInit, Component, ElementRef, Inject, NgZone, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatButton, MatIconButton } from "@angular/material/button";
import {
    MAT_DIALOG_DATA,
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogRef,
    MatDialogTitle
} from "@angular/material/dialog";
import { BacklogItem } from "@interfaces/boards/backlog/backlog.item";
import { Observable, Subject, take, takeUntil } from "rxjs";
import { Sprint } from "@interfaces/boards/backlog/sprint";
import { User } from "@interfaces/boards/user";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { CustomValidators } from "@core/validators/custom-validators";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { matDone, matEdit } from "@ng-icons/material-icons/baseline";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { MatError, MatHint, MatSuffix } from "@angular/material/form-field";
import {
    MatFormField,
    MatLabel,
    MatOption,
    MatSelect,
    MatSelectChange,
    MatSelectTrigger
} from "@angular/material/select";
import { SprintService } from "@core/services/boards/backlog/sprint/sprint.service";
import { CdkScrollable, ScrollDispatcher } from "@angular/cdk/scrolling";
import { BacklogItemType } from "@core/enum/BacklogItemType";
import { BacklogTypeComponent } from "@pages/boards/backlog/backlog-item-table/backlog-type/backlog-type.component";
import { LowerCasePipe, NgIf } from "@angular/common";
import { UserAvatarComponent } from "@pages/utils/user-avatar/user-avatar.component";
import { UserService } from "@core/services/users/user.service";
import { StatusSelectComponent } from "@pages/boards/backlog/backlog-item-table/status-select/status-select.component";
import {
    BacklogItemCommentListComponent
} from "@pages/boards/backlog/backlog-item-comment-list/backlog-item-comment-list.component";
import { MatInput } from "@angular/material/input";
import { BacklogItemService } from "@core/services/boards/backlog/backlog-item/backlog-item.service";

@Component({
    selector: 'app-backlog-item-details',
    standalone: true,
    imports: [
        MatButton,
        MatDialogActions,
        MatDialogClose,
        MatInput,
        MatDialogTitle,
        MatDialogContent,
        ReactiveFormsModule,
        MatFormField,
        MatIconButton,
        NgIcon,
        MatSuffix,
        MatError,
        CdkTextareaAutosize,
        MatHint,
        MatLabel,
        MatOption,
        MatSelect,
        BacklogTypeComponent,
        MatSelectTrigger,
        LowerCasePipe,
        UserAvatarComponent,
        NgIf,
        StatusSelectComponent,
        BacklogItemCommentListComponent
    ],
    providers: [provideIcons({matEdit, matDone})],
    templateUrl: './backlog-item-details.component.html',
    styleUrl: './backlog-item-details.component.scss'
})
export class BacklogItemDetailsComponent implements OnInit, AfterViewInit, OnDestroy {

    constructor(@Inject(MAT_DIALOG_DATA) public data: BacklogItem,
                public dialogRef: MatDialogRef<BacklogItemDetailsComponent>,
                private sprintService: SprintService,
                private userService: UserService,
                private scrollDispatcher: ScrollDispatcher,
                private ngZone: NgZone,
                private backlogItemService: BacklogItemService) {
    }

    isEditingTitle: boolean = false;
    isEditingDescription: boolean = false;

    tmpItem!: BacklogItem;

    private readonly sprintPageSize: number = 20;
    private sprintsPageNumber: number = 0;
    @ViewChild('sprintSelect') sprintSelect !: MatSelect;

    private readonly usersPageSize: number = 20;
    private usersPageNumber: number = 0;
    @ViewChild('assigneeSelect') assigneeSelect !: MatSelect;

    @ViewChild('dialogContent') dialogContent!: ElementRef;

    destroy$: Subject<void> = new Subject<void>();

    sprints: Sprint[] = [];
    users: User[] = [];

    itemForm!: FormGroup;
    types: BacklogItemType[] = [
        BacklogItemType.STORY,
        BacklogItemType.BUG,
        BacklogItemType.TASK,
        BacklogItemType.EPIC
    ];

    unassigned: User = {
        userId: -1,
        name: '',
        surname: '',
        username: ''

    }

    selectedAssignee!: User;

    ngOnInit(): void {
        this.tmpItem = {
            backlogItemId: this.data.backlogItemId,
            title: this.data.title,
            description: this.data.description,
            status: this.data.status,
            itemType: this.data.itemType,
            assignee: this.data.assignee ? this.data.assignee : this.unassigned,
            sprintId: this.data.sprintId,
            projectId: this.data.projectId,
        }

        this.sprintService.getSprintsOnPageForProject(1, this.sprintsPageNumber).pipe(take(1)).subscribe(
            sprints => {
                this.sprints = sprints;
            })
        this.userService.getProjectMembersOnPage(1, this.usersPageNumber).pipe(take(1)).subscribe(
            users => {
                this.users = users;
            })
        this.selectedAssignee = this.tmpItem.assignee;

        this.itemForm = new FormGroup({
            title: new FormControl(this.tmpItem.title, [
                Validators.required,
                Validators.maxLength(100),
                CustomValidators.notWhitespace(),
            ]),
            description: new FormControl(this.tmpItem.description, [
                Validators.required,
                Validators.maxLength(500),
                CustomValidators.notWhitespace(),
            ]),
            type: new FormControl(this.tmpItem.itemType, [
                Validators.required
            ]),
            sprint: new FormControl(this.tmpItem.sprintId, [
            ]),
            assignee: new FormControl(this.tmpItem.assignee.userId, [])
        });

    }

    ngAfterViewInit(): void {
        this.subscribeToScrollEvent(this.sprintSelect).pipe(takeUntil(this.destroy$)).subscribe( result => {
            if(result) {
                if (this.sprints.length % this.sprintPageSize !== 0) {
                    return;
                }
                this.sprintsPageNumber++;
                this.sprintService.getSprintsOnPageForProject(1, this.sprintsPageNumber).pipe(take(1)).subscribe(newSprints => {
                    this.ngZone.run(() => {
                        this.sprints = [...this.sprints, ...newSprints];
                    })
                })
            }
        })

        this.subscribeToScrollEvent(this.assigneeSelect).pipe(takeUntil(this.destroy$)).subscribe(result => {
            if(result) {
                if (this.users.length % this.usersPageSize !== 0) {
                    return;
                }
                this.usersPageNumber++;
                this.userService.getProjectMembersOnPage(1, this.usersPageNumber).pipe(take(1)).subscribe(newUsers => {
                    this.ngZone.run(() => {
                        this.users = [...this.users, ...newUsers];
                    })
                })
            }
        })
    }

    changeAssignee(event: MatSelectChange) {
        if(event.value == -1) {
            this.selectedAssignee = this.unassigned;
        } else {
            this.selectedAssignee = this.users.find(user => user.userId === event.value)!;
        }
    }

    subscribeToScrollEvent(select: MatSelect): Observable<boolean> {
        const scrollBottomSubject: Subject<boolean> = new Subject<boolean>();
        select.openedChange.pipe(takeUntil(this.destroy$)).subscribe(opened => {
            if (!opened) {
                return;
            }
            const scrollable: CdkScrollable = new CdkScrollable(select.panel, this.scrollDispatcher, this.ngZone);
            scrollable.elementScrolled().pipe(takeUntil(this.destroy$)).subscribe(() => {
                const element: HTMLElement = scrollable.getElementRef().nativeElement;
                const atBottom = element.scrollTop + element.offsetHeight >= element.scrollHeight;
                scrollBottomSubject.next(atBottom);
            })
        })
        return scrollBottomSubject.asObservable();
    }


    saveItem() {
        if(this.itemForm.invalid) {
            return;
        }
        this.tmpItem.title = this.itemForm.get('title')!.value;
        this.tmpItem.description = this.itemForm.get('description')!.value;
        this.tmpItem.assignee = this.selectedAssignee;
        console.log(this.tmpItem);
        this.dialogRef.close(this.tmpItem);
    }

    scrollToBottom() {
        setTimeout(() => {
            this.dialogContent.nativeElement.scrollTop = this.dialogContent.nativeElement.scrollHeight;
        }, 0);
    }

    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }


}
