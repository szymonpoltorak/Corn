import { Component, Input } from '@angular/core';
import { MatRipple } from "@angular/material/core";
import { NgOptimizedImage, SlicePipe } from "@angular/common";
import { MatTooltip } from "@angular/material/tooltip";
import { UserAvatarComponent } from "@pages/utils/user-avatar/user-avatar.component";
import { StorageService } from "@core/services/storage.service";
import { Project } from "@interfaces/boards/project";
import { Router } from "@angular/router";

@Component({
    selector: 'app-project',
    standalone: true,
    imports: [
        MatRipple,
        SlicePipe,
        NgOptimizedImage,
        MatTooltip,
        UserAvatarComponent
    ],
    templateUrl: './project.component.html',
    styleUrl: './project.component.scss'
})
export class ProjectComponent {

    constructor(private storageService: StorageService,
                private router: Router) {
    }

    @Input() project!: Project;

    chooseProject(): void {
        this.storageService.saveProjectId(this.project.projectId);
        this.router.navigate(['/boards'])
    }
}
