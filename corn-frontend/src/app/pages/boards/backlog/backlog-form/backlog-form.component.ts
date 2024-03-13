import { AfterViewInit, Component, NgZone, OnDestroy, OnInit, ViewChild } from '@angular/core';
import {
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogRef,
    MatDialogTitle
} from "@angular/material/dialog";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { CustomValidators } from "@core/validators/custom-validators";
import { MatError, MatHint } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatButton, } from "@angular/material/button";
import {
    MatFormField,
    MatLabel,
    MatOption,
    MatSelect,
    MatSelectChange,
    MatSelectTrigger
} from "@angular/material/select";
import { BacklogItemType } from "@core/enum/BacklogItemType";
import { MatTooltip } from "@angular/material/tooltip";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { bootstrapBugFill } from "@ng-icons/bootstrap-icons";
import { featherBook } from "@ng-icons/feather-icons";
import { matTask } from "@ng-icons/material-icons/baseline";
import { octContainer } from "@ng-icons/octicons";
import { User } from "@core/interfaces/boards/user";
import { UserAvatarComponent } from "@pages/utils/user-avatar/user-avatar.component";
import { MatIcon } from "@angular/material/icon";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { Sprint } from "@interfaces/boards/backlog/sprint";
import { CdkScrollable, ScrollDispatcher, ScrollingModule } from "@angular/cdk/scrolling";
import { Subscription } from "rxjs";
import { SprintService } from "@core/services/boards/backlog/sprint/sprint.service";
import { UserService } from "@core/services/users/user.service";

@Component({
    selector: 'app-backlog-form',
    standalone: true,
    imports: [
        MatDialogTitle,
        MatDialogContent,
        ReactiveFormsModule,
        MatError,
        MatFormField,
        MatInput,
        MatLabel,
        MatDialogActions,
        MatDialogClose,
        MatButton,
        MatHint,
        MatSelect,
        MatOption,
        MatTooltip,
        NgIcon,
        UserAvatarComponent,
        MatSelectTrigger,
        MatIcon,
        CdkTextareaAutosize,
        ScrollingModule
    ],
    templateUrl: './backlog-form.component.html',
    providers: [provideIcons({ bootstrapBugFill, featherBook, matTask, octContainer })],
    styleUrl: './backlog-form.component.scss'
})
export class BacklogFormComponent implements AfterViewInit, OnDestroy {

    private readonly sprintPageSize = 20;
    private sprintsPageNumber = 0;

    private readonly membersPageSize = 20;
    private membersPageNumber = 0;

    sprintScrollSubscription?: Subscription;
    memberScrollSubscription?: Subscription;

    sprints: Sprint[] = [];

    currentUser ?: User;
    currentType ?: BacklogItemType;

    @ViewChild('sprintSelect') sprintSelect !: MatSelect;
    @ViewChild('memberSelect') memberSelect !: MatSelect;

    itemForm = new FormGroup({
        title: new FormControl('', [
            Validators.required,
            Validators.maxLength(100),
            CustomValidators.notWhitespace(),
        ]),
        description: new FormControl('', [
            Validators.required,
            Validators.maxLength(500),
            CustomValidators.notWhitespace(),
        ]),
        type: new FormControl('', [
            Validators.required
        ]),
        assignee: new FormControl('', [
            Validators.required
        ]),
        sprint: new FormControl('', [
            Validators.required
        ])
    });

    constructor(public dialogRef: MatDialogRef<BacklogFormComponent>,
                private scrollDispatcher: ScrollDispatcher,
                private ngZone: NgZone,
                private sprintService: SprintService,
                private userService: UserService) {
    }

    types: BacklogItemType[] = [
        BacklogItemType.STORY,
        BacklogItemType.BUG,
        BacklogItemType.TASK,
        BacklogItemType.EPIC
    ];
    users: User[] = [];

    submitForm(): void {
        if (this.itemForm.valid) {
            let data = {
                title: this.itemForm.get('title')?.value,
                description: this.itemForm.get('description')?.value,
                sprintId: this.itemForm.get('sprint')?.value,
                type: this.itemForm.get('type')?.value,
                assignee: this.itemForm.get('assignee')?.value
            };

            this.dialogRef.close(data);
        }
    }
    changeCurrentUser(event: MatSelectChange): void {
        this.currentUser = event.value;
    }

    changeCurrentType(event: MatSelectChange): void {
        this.currentType = event.value;
    }


    protected readonly BacklogItemType = BacklogItemType;

    ngAfterViewInit(): void {
        this.userService.getProjectMembersOnPage(1, this.membersPageNumber).subscribe(users => {
            this.users = users;
        });
        this.sprintService.getSprintsOnPageForProject(1, this.sprintsPageNumber).subscribe(sprints => {
            this.sprints = sprints;
        });

        this.sprintSelect.openedChange.subscribe(opened => {
            if (opened) {
                const scrollable = new CdkScrollable(this.sprintSelect.panel, this.scrollDispatcher, this.ngZone);
                this.sprintScrollSubscription = scrollable.elementScrolled().subscribe(() => {
                    this.ngZone.runOutsideAngular(() => {
                        if (this.sprints.length % this.sprintPageSize !== 0) {
                            return;
                        }
                        const element = scrollable.getElementRef().nativeElement;
                        const atBottom = element.scrollTop + element.offsetHeight >= element.scrollHeight;
                        if (atBottom) {
                            this.sprintsPageNumber++;
                            this.sprintService.getSprintsOnPageForProject(1, this.sprintsPageNumber).subscribe(newSprints => {
                                this.ngZone.run(() => {
                                    this.sprints = [...this.sprints, ...newSprints];
                                })
                            })
                        }
                    })
                });
            }
        })

        this.memberSelect.openedChange.subscribe(opened => {
            if (opened) {
                const scrollable: CdkScrollable = new CdkScrollable(this.memberSelect.panel, this.scrollDispatcher, this.ngZone);
                this.memberScrollSubscription = scrollable.elementScrolled().subscribe(() => {
                    this.ngZone.runOutsideAngular(() => {
                        if (this.users.length % this.membersPageSize !== 0) {
                            return;
                        }
                        const element = scrollable.getElementRef().nativeElement;
                        const atBottom = element.scrollTop + element.offsetHeight >= element.scrollHeight;
                        if (atBottom) {
                            this.membersPageNumber++;
                            this.userService.getProjectMembersOnPage(1, this.membersPageNumber).subscribe(newUsers => {
                                this.ngZone.run(() => {
                                    this.users = [...this.users, ...newUsers];
                                })
                            })
                        }
                    })
                })
            }
        })
    }

    ngOnDestroy(): void {
        this.sprintScrollSubscription?.unsubscribe();
    }


}
