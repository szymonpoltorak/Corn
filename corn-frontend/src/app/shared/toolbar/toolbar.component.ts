import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { MatToolbar } from "@angular/material/toolbar";
import { KeycloakProfile } from "keycloak-js";
import { UserinfoComponent } from "@pages/boards/userinfo/userinfo.component";
import { KeycloakService } from "keycloak-angular";
import { Router, RouterLink } from "@angular/router";
import { RouterPaths } from "@core/enum/RouterPaths";
import { MatTooltip } from "@angular/material/tooltip";
import { StorageService } from "@core/services/storage.service";

@Component({
    selector: 'app-toolbar',
    standalone: true,
    imports: [
        MatButton,
        MatIcon,
        MatIconButton,
        MatMenu,
        MatMenuItem,
        MatToolbar,
        UserinfoComponent,
        MatMenuTrigger,
        MatTooltip,
        RouterLink
    ],
    templateUrl: './toolbar.component.html',
    styleUrl: './toolbar.component.scss'
})
export class ToolbarComponent implements OnInit {
    @Input() isOnProjectRoute: boolean = false;
    userProfile ?: KeycloakProfile;
    isLoggedIn: boolean = false;
    @Output() sidebarEvent: EventEmitter<boolean> = new EventEmitter<boolean>();
    sidebarShown: boolean = false;

    constructor(private keycloak: KeycloakService,
                private router: Router,
                private storage: StorageService) {
    }

    async ngOnInit(): Promise<void> {
        this.isLoggedIn = this.keycloak.isLoggedIn();

        if (this.isLoggedIn) {
            this.userProfile = await this.keycloak.loadUserProfile();
        } else {
            this.router.navigate([RouterPaths.HOME_DIRECT_PATH]);
        }
    }

    toggleSidebar(): void {
        this.sidebarShown = !this.sidebarShown;

        this.sidebarEvent.emit(this.sidebarShown);
    }

    logout(): void {
        this.storage.deleteProjectFromStorage();
        this.keycloak.logout();
    }

    protected readonly RouterPaths = RouterPaths;
}
