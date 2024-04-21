import { Component, HostListener, OnInit } from '@angular/core';
import { ProjectComponent } from "@pages/project-list/project/project.component";
import { MatGridList, MatGridTile } from "@angular/material/grid-list";
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { take } from "rxjs";
import { ProjectService } from "@core/services/boards/project.service";
import { Project } from "@interfaces/boards/project";
import { MatFabButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatTooltip } from "@angular/material/tooltip";
import { MatDialog } from "@angular/material/dialog";
import { NewProjectComponent } from "@pages/project-list/new-project/new-project.component";
import { RouterPaths } from "@core/enum/RouterPaths";
import { StorageService } from "@core/services/storage.service";
import { Router } from "@angular/router";

@Component({
    selector: 'app-project-list',
    standalone: true,
    imports: [
        ProjectComponent,
        MatGridList,
        MatGridTile,
        ToolbarComponent,
        MatIcon,
        MatFabButton,
        MatTooltip
    ],
    templateUrl: './project-list.component.html',
    styleUrl: './project-list.component.scss'
})
export class ProjectListComponent implements OnInit {

    cols: number = 5;

    pageNumber: number = 0;

    projects: Project[] = [];

    gotAllProjects: boolean = false;

    constructor(private projectService: ProjectService,
                private dialog: MatDialog,
                private storageService: StorageService,
                private router: Router) {
    }

    ngOnInit(): void {
        this.getProjects();
    }

    @HostListener('window:scroll', ['$event'])
    onScroll(event: any): void {
        if (this.gotAllProjects) {
            return;
        }

        const position: number = (document.documentElement.scrollTop || document.body.scrollTop) + window.innerHeight;
        const max: number = document.documentElement.scrollHeight;

        if (position >= max) {
            this.pageNumber++;

            this.getProjects();
        }
    }

    openCreateProjectForm(): void {
        const dialogRef = this.dialog.open(NewProjectComponent, {
            enterAnimationDuration: '300ms',
            exitAnimationDuration: '100ms',
        });

        dialogRef
            .afterClosed()
            .pipe(take(1))
            .subscribe((newProjectName: string) => {
                if (newProjectName === null || newProjectName === "") {
                    return;
                }
                this.projectService
                    .createNewProject(newProjectName)
                    .pipe(take(1))
                    .subscribe((project: Project) => {
                        this.storageService.saveProject(project);

                        this.router.navigate([RouterPaths.BOARDS_DIRECT_PATH, RouterPaths.BACKLOG_PATH]);
                    });
            });
    }

    private getProjects(): void {
        this.projectService
            .getProjectsOnPage(this.pageNumber)
            .pipe(take(1))
            .subscribe((items: Project[]) => {
                    if (items.length == 0) {
                        this.gotAllProjects = true;
                        return;
                    }
                    this.projects = [...this.projects, ...items];
                }
            )
    }
}
