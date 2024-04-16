import { Component, HostListener, OnInit } from '@angular/core';
import { ProjectComponent } from "@pages/project-list/project/project.component";
import { MatGridList, MatGridTile } from "@angular/material/grid-list";
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { take } from "rxjs";
import { ProjectService } from "@core/services/boards/project.service";
import { Project } from "@interfaces/boards/project";

@Component({
    selector: 'app-project-list',
    standalone: true,
    imports: [
        ProjectComponent,
        MatGridList,
        MatGridTile,
        ToolbarComponent,
    ],
    templateUrl: './project-list.component.html',
    styleUrl: './project-list.component.scss'
})
export class ProjectListComponent implements OnInit {

    cols: number = 5;

    pageNumber: number = 0;

    projects: Project[] = [];

    gotAllProjects: boolean = false;

    constructor(private projectService: ProjectService) {
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

    private getProjects(): void {
        this.projectService.getProjectsOnPage(this.pageNumber).pipe(take(1)).subscribe(
            (items: any) => {
                if (items.length == 0) {
                    this.gotAllProjects = true;
                    return;
                }
                this.projects = [...this.projects, ...items];
            }
        )
    }
}
