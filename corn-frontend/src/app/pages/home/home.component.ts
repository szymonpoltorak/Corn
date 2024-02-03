import { Component } from '@angular/core';
import { MatCard, MatCardHeader } from "@angular/material/card";
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatFormField, MatHint } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatToolbar } from "@angular/material/toolbar";
import { MatIcon } from "@angular/material/icon";

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [
        MatToolbar,
        MatIcon,
        MatButton
    ],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss'
})
export class HomeComponent {

}
