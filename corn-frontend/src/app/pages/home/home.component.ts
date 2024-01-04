import { Component } from '@angular/core';
import { KeycloakService } from "keycloak-angular";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent {
    constructor(private keycloakService: KeycloakService) {
    }

    redirectToLoginServer(): void {
        this.keycloakService.login();
    }
}
