import { Component, Inject, OnInit } from '@angular/core';
import {
    MAT_DIALOG_DATA,
    MatDialogActions,
    MatDialogContent,
    MatDialogModule,
    MatDialogRef
} from "@angular/material/dialog";
import { Sprint } from "@interfaces/boards/backlog/sprint";
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatNativeDateModule } from "@angular/material/core";
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";

@Component({
    selector: 'app-backlog-edit-form',
    standalone: true,
    imports: [
        MatDialogContent,
        MatDialogActions,
        MatButtonModule,
        MatDialogModule,
        MatFormFieldModule,
        MatInputModule,
        MatDatepickerModule,
        MatNativeDateModule,
        ReactiveFormsModule
    ],
    templateUrl: './backlog-edit-form.component.html',
    styleUrl: './backlog-edit-form.component.scss'
})
export class BacklogEditFormComponent implements OnInit {

    protected formGroup !: FormGroup;

    constructor(@Inject(MAT_DIALOG_DATA) public data: Sprint,
                public dialogRef: MatDialogRef<BacklogEditFormComponent>,
                private formBuilder: FormBuilder) {
    }

    ngOnInit(): void {
        this.formGroup = this.formBuilder.group({
            sprintName: new FormControl(this.data.sprintName, [
                Validators.required
            ]),
            goal: new FormControl(this.data.sprintDescription, []),
            startDate: new FormControl(this.data.startDate, [
                Validators.required
            ]),
            endDate: new FormControl(this.data.endDate, [
                Validators.required
            ])
        });
    }

}
