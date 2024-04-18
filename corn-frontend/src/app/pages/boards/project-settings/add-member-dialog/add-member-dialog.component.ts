import { Component } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { MatButton } from "@angular/material/button";
import { MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef } from "@angular/material/dialog";
import { MatError, MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { CustomValidators } from "@core/validators/custom-validators";

@Component({
    selector: 'app-add-member-dialog',
    standalone: true,
    imports: [
        FormsModule,
        MatButton,
        MatDialogActions,
        MatDialogClose,
        MatDialogContent,
        MatError,
        MatFormField,
        MatHint,
        MatInput,
        MatLabel,
        ReactiveFormsModule
    ],
    templateUrl: './add-member-dialog.component.html',
    styleUrl: './add-member-dialog.component.scss'
})
export class AddMemberDialogComponent {
    nameControl: FormControl<string | null> = new FormControl<string>("", [
        Validators.required,
        Validators.maxLength(120),
        Validators.minLength(2),
        Validators.email,
        CustomValidators.notWhitespace()
    ]);

    constructor(public dialogRef: MatDialogRef<AddMemberDialogComponent>) {
    }

    addNewProjectMember(): void {
        if (this.nameControl.invalid) {
            return;
        }
        const memberUsername: string = this.nameControl.value as string;

        this.dialogRef.close(memberUsername);
    }
}
