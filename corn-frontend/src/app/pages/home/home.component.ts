import { Component } from '@angular/core';
import { MatCard, MatCardHeader } from "@angular/material/card";
import { MatButton } from "@angular/material/button";
import { MatFormField, MatHint } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [
        MatCard,
        MatCardHeader,
        MatButton,
        MatFormField,
        MatInput,
        MatHint
    ],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss'
})
export class HomeComponent {

}
