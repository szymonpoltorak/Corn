import { Component } from '@angular/core';
import {
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogRef,
    MatDialogTitle
} from "@angular/material/dialog";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { CustomValidators } from "@core/validators/custom-validators";
import { MatError, MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatButton, } from "@angular/material/button";
import { MatOption, MatSelect, MatSelectChange, MatSelectTrigger } from "@angular/material/select";
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
        CdkTextareaAutosize
    ],
    templateUrl: './backlog-form.component.html',
    providers: [provideIcons({ bootstrapBugFill, featherBook, matTask, octContainer })],
    styleUrl: './backlog-form.component.scss'
})
export class BacklogFormComponent {

    currentUser ?: User;
    currentType ?: BacklogItemType;

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
        ])
    });

    constructor(public dialogRef: MatDialogRef<BacklogFormComponent>) {
    }

    types: BacklogItemType[] = [
        BacklogItemType.STORY,
        BacklogItemType.BUG,
        BacklogItemType.TASK,
        BacklogItemType.EPIC
    ];

    users: User[] = [
        { userId: 0, name: 'John', surname: 'Doe' },
        { userId: 1, name: 'Szymon', surname: 'Kowalski' },
        { userId: 2, name: 'Andrzej', surname: 'Switch' }
    ];

    submitForm(): void {
        if (this.itemForm.valid) {
            let data = {
                title: this.itemForm.get('title')?.value,
                description: this.itemForm.get('description')?.value,
                type: this.itemForm.get('type')?.value,
                assignee: this.itemForm.get('assignee')?.value
            };

            this.dialogRef.close(data);
        }
    }

    changeCurrentUser(event: MatSelectChange): void {
        this.currentUser = this.users.find(user => user.userId === event.value);
    }

    changeCurrentType(event: MatSelectChange): void {
        this.currentType = event.value;
    }


    protected readonly BacklogItemType = BacklogItemType;
}
