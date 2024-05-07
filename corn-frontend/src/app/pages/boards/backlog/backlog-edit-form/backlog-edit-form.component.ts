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
import { SprintEditData } from "@interfaces/boards/backlog/sprint-edit-data.interfaces";
import { DatePipe } from "@angular/common";

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
                private formBuilder: FormBuilder,
                private datePipe: DatePipe) {
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

    submitSprintEdit(): void {
        if (this.formGroup.invalid) {
            return;
        }
        const sprintsName: string = this.formGroup.get('sprintName')?.value;
        const goal: string = this.formGroup.get('goal')?.value || "";
        const startDate: Date = this.formGroup.get('startDate')?.value;
        const endDate: Date = this.formGroup.get('endDate')?.value;

        const sprintRequest: SprintEditData = {
            sprintName: sprintsName,
            goal: goal,
            startDate: this.datePipe.transform(startDate, "yyyy-MM-dd") as string,
            endDate: this.datePipe.transform(endDate, "yyyy-MM-dd") as string
        };

        this.dialogRef.close(sprintRequest);
    }
}
