import { Component, Input, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule, Location } from '@angular/common';
import { SidebarButton } from './sidebar-button/sidebar-button.component';
import { MatToolbar, MatToolbarRow } from '@angular/material/toolbar';
import { MatTab, MatTabGroup } from '@angular/material/tabs';
import { FeatureComponent } from '@pages/home/feature/feature.component';
import { UserinfoComponent } from '@pages/boards/userinfo/userinfo.component';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';
import { MatMenuModule, MatMenuTrigger } from '@angular/material/menu';

@Component({
    selector: 'app-boards',
    standalone: true,
    imports: [
        RouterOutlet,
        MatSidenavModule,
        MatFormFieldModule,
        MatSelectModule,
        MatButtonModule,
        MatIconModule,
        SidebarButton,
        MatToolbar,
        MatToolbarRow,
        MatTabGroup,
        MatTab,
        FeatureComponent,
        UserinfoComponent,
        MatMenuTrigger,
        MatMenuModule,
        CommonModule,
    ],
    templateUrl: './boards.component.html',
})
export class BoardsComponent implements OnInit {

    @Input() sidebarShown: boolean = true;
    @Input() projectName: string = 'Your Project';

    selected: string = '';

    isLoggedIn: boolean = false;
    userProfile?: KeycloakProfile;

    constructor(
        readonly router: Router,
        readonly location: Location,
        readonly keycloak: KeycloakService,
    ) {
    }

    async ngOnInit() {
        this.selected = this.location.path().split('/').pop() || '';
        this.router.events.subscribe((val) => {
            if (val instanceof NavigationEnd) {
                this.selected = val.url.split('/').pop() || '';
            }
        });
        this.isLoggedIn = this.keycloak.isLoggedIn();
        if (this.isLoggedIn) {
            this.userProfile = await this.keycloak.loadUserProfile();
            console.log(this.userProfile);
        }
    }

    navigateToBacklog(): void {
        this.router.navigate(['/boards/backlog']);
    }

    navigateToTimeline(): void {
        this.router.navigate(['/boards/timeline']);
    }

    toggleSidebar(): void {
        this.sidebarShown = !this.sidebarShown;
    }

    logout(): void {
        this.keycloak.logout();
    }

}
