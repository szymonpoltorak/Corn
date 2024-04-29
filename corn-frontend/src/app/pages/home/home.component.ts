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
import { RouterPaths } from "@core/enum/RouterPaths";

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
            title: "All projects in one place",
            imagePath: "assets/home/projects_list.png"
        },
        {
            content: "You can easily plan new sprints and manage them. Everything is very simple.",
            isLeft: true,
            label: "Manage Sprints",
            title: "One view to rule them all",
            imagePath: "assets/home/sprints.png"
        },
        {
            content: "Add, remove and edit tasks in appropriate sprint. Manage descriptions, add comments and assign them to users.",
            isLeft: false,
            label: "Work with Tasks",
            title: "Easy to add, easy to remove",
            imagePath: "assets/home/task.png"
        },
        {
            content: "You can easily analyze your sprints and see how your team is doing. You can see how much work is done and how much is left to do",
            isLeft: true,
            label: "Analyze your sprints",
            title: "Analysis is a key",
            imagePath: "assets/home/sprint_burndown.png"
        }
    ];
}
