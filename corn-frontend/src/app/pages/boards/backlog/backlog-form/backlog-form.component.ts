import { Component } from '@angular/core';
import {
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {CustomValidators} from "@core/validators/custom-validators";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";

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
    MatButton
  ],
  templateUrl: './backlog-form.component.html',
  styleUrl: './backlog-form.component.scss'
})
export class BacklogFormComponent {

  itemForm = new FormGroup({
    title: new FormControl('', [
      Validators.required,
      Validators.maxLength(100),
      CustomValidators.notWhitespace(),
    ]),
    description: new FormControl('', [
      Validators.required,
      Validators.maxLength(500),
      CustomValidators.notWhitespace,
    ]),
    type: new FormControl('', [
      Validators.required
    ]),
  });
  constructor(public dialogRef: MatDialogRef<BacklogFormComponent>) {}

  submitForm() {

  }

  closeDialog() {
    
  }
}
