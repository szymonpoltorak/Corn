import { Component } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatToolbar } from "@angular/material/toolbar";
import { MatIcon } from "@angular/material/icon";
import { MatTab, MatTabGroup } from "@angular/material/tabs";

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [
        MatToolbar,
        MatIcon,
        MatButton,
        MatTabGroup,
        MatTab
    ],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss'
})
export class HomeComponent {

}
