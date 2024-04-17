import { Component, OnInit } from '@angular/core';
import { MatCardModule } from "@angular/material/card";
import { StorageService } from "@core/services/storage.service";
import { StorageKey } from "@core/enum/storage-key.enum";
import { MatDivider } from "@angular/material/divider";
import { MatButton, MatFabButton, MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatFormField } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { MatLabel } from "@angular/material/select";
import { UserAvatarComponent } from "@pages/utils/user-avatar/user-avatar.component";
import { User } from "@interfaces/boards/user";
import { NgIcon } from "@ng-icons/core";
import { CdkDrag } from "@angular/cdk/drag-drop";
import { KeycloakProfile } from "keycloak-js";
import { MatPaginator } from "@angular/material/paginator";
import { MatDialog } from "@angular/material/dialog";
import { NewProjectComponent } from "@pages/project-list/new-project/new-project.component";
import { take } from "rxjs";
import { Project } from "@interfaces/boards/project";
import { RouterPaths } from "@core/enum/RouterPaths";
import { ProjectService } from "@core/services/boards/project.service";

export interface PeriodicElement {
    fullName: string;
    username: number | string;
}

@Component({
    selector: 'app-project-settings',
    standalone: true,
    imports: [
        MatCardModule,
        MatDivider,
        MatIconButton,
        MatIcon,
        MatButton,
        MatFormField,
        MatInput,
        MatLabel,
        MatTableModule,
        UserAvatarComponent,
        MatFabButton,
        NgIcon,
        CdkDrag,
        MatPaginator
    ],
    templateUrl: './project-settings.component.html',
    styleUrl: './project-settings.component.scss'
})
export class ProjectSettingsComponent implements OnInit {
    projectName !: string;
    user: User = {
        username: "user@gmail.com",
        name: "name",
        surname: "surname",
        userId: 1
    }
    currentUser !: KeycloakProfile;
    displayedColumns: string[] = ['fullName', 'username', 'deleteMember'];
    dataSource !: MatTableDataSource<User>;
    isProjectOwner: boolean = true;

    constructor(private storage: StorageService,
                private projectService: ProjectService,
                private dialog: MatDialog) {
    }

    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;

        this.dataSource.filter = filterValue.trim().toLowerCase();
    }

    ngOnInit(): void {
        this.currentUser = JSON.parse(this.storage.getValueFromStorage(StorageKey.CURRENT_USER));
        this.projectName = this.storage.getValueFromStorage(StorageKey.PROJECT_NAME);
        this.dataSource = new MatTableDataSource<User>([this.user]);
    }

    deleteMember(projectMember: PeriodicElement): void {

    }

    editProjectName(): void {
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
                        this.storage.saveProject(project);

                        this.projectName = project.name;
                    });
            });
    }
}
