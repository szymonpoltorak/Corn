import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatButton } from "@angular/material/button";

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet, MatButton],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})
export class AppComponent {
    title = 'corn-frontend';
}
