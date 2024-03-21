import { Component, OnInit } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatToolbar } from "@angular/material/toolbar";
import { MatIcon } from "@angular/material/icon";
import { MatTab, MatTabGroup } from "@angular/material/tabs";
import { FeatureComponent } from "@pages/home/feature/feature.component";
import { Feature } from "@core/interfaces/home/feature.interface";
import { KeycloakService } from 'keycloak-angular';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { Router } from '@angular/router';
import { RouterPaths } from "@core/enum/router-paths";

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [
        MatToolbar,
        MatIcon,
        MatButton,
        MatTabGroup,
        MatTab,
        FeatureComponent,
        CommonModule,
        NgOptimizedImage,
    ],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

    constructor(
        readonly router: Router,
        readonly keycloak: KeycloakService
    ) {
    }

    ngOnInit() {
        if (this.keycloak.isLoggedIn()) {
            this.router.navigate([`${RouterPaths.PROJECT_LIST_DIRECT_PATH}`]);
        }
    }

    login(): void {
        this.keycloak.login();
    }

    register(): void {
        this.keycloak.register();
    }

    protected readonly features: Feature[] = [
        {
            content: "We offer easy way to manage your different projects. Each project is one place allowing you to switch between them easily.",
            isLeft: false,
            label: "Manage Projects",
            title: "All projects in one place"
        },
        {
            content: "You can easily plan new sprints and manage them. Everything is very simple.",
            isLeft: true,
            label: "Manage Sprints",
            title: "One view to rule them all"
        },
        {
            content: "Add, remove and edit tasks in appropriate sprint. Manage descriptions, add comments and assign them to users.",
            isLeft: false,
            label: "Work with Tasks",
            title: "Easy to add, easy to remove"
        },
        {
            content: "Manage your backlog entries to easily deal with work to do. Easy view for whole work.",
            isLeft: true,
            label: "Deal with Backlog",
            title: "All tasks in one backlog"
        }
    ];
}
