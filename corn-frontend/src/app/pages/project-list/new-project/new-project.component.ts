import { Component } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef } from "@angular/material/dialog";
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { MatError, MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { CustomValidators } from "@core/validators/custom-validators";

@Component({
    selector: 'app-new-project',
    standalone: true,
    imports: [
        MatButton,
        MatDialogClose,
        MatDialogActions,
        MatDialogContent,
        FormsModule,
        MatError,
        MatFormField,
        MatInput,
        MatLabel,
        ReactiveFormsModule,
        MatHint
    ],
    templateUrl: './new-project.component.html',
    styleUrl: './new-project.component.scss'
})
export class NewProjectComponent {
    nameControl: FormControl<string | null> = new FormControl<string>("", [
        Validators.required,
        Validators.maxLength(120),
        Validators.minLength(2),
        CustomValidators.notWhitespace()
    ]);

    constructor(public dialogRef: MatDialogRef<NewProjectComponent>) {
    }

    createNewProject(): void {
        console.log(this.nameControl.invalid);

        if (this.nameControl.invalid) {
            return;
        }
        const projectName: string = this.nameControl.value as string;

        console.log(projectName);

        this.dialogRef.close(projectName);
    }
}
