import { Component, OnInit } from '@angular/core';
import { MatCardModule } from "@angular/material/card";
import { StorageService } from "@core/services/storage.service";
import { StorageKey } from "@core/enum/storage-key.enum";
import { MatDivider } from "@angular/material/divider";
import { MatButton, MatFabButton, MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { MatLabel } from "@angular/material/select";
import { UserAvatarComponent } from "@pages/utils/user-avatar/user-avatar.component";
import { User } from "@interfaces/boards/user";
import { NgIcon } from "@ng-icons/core";
import { KeycloakProfile } from "keycloak-js";
import { MatPaginator } from "@angular/material/paginator";
import { MatDialog } from "@angular/material/dialog";
import { NewProjectComponent } from "@pages/project-list/new-project/new-project.component";
import { take } from "rxjs";
import { Project } from "@interfaces/boards/project";
import { ProjectService } from "@core/services/boards/project.service";
import { Route, Router } from "@angular/router";
import { RouterPaths } from "@core/enum/RouterPaths";
import {
    AddMemberDialogComponent
} from "@pages/boards/project-settings/add-member-dialog/add-member-dialog.component";

@Component({
    selector: 'app-project-settings',
    standalone: true,
    imports: [
        MatCardModule,
        MatDivider,
        MatIconButton,
        MatIcon,
        MatButton,
        MatLabel,
        MatTableModule,
        UserAvatarComponent,
        MatFabButton,
        NgIcon,
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
    //TODO: add checking if user is project owner or not
    isProjectOwner: boolean = true;
    pageNumber: number = 0;
    projectId !: number;
    totalNumber !: number;

    constructor(private storage: StorageService,
                private projectService: ProjectService,
                private router: Router,
                private dialog: MatDialog) {
    }

    ngOnInit(): void {
        this.currentUser = JSON.parse(this.storage.getValueFromStorage(StorageKey.CURRENT_USER));
        this.projectName = this.storage.getValueFromStorage(StorageKey.PROJECT_NAME);
        this.projectId = this.storage.getValueFromStorage(StorageKey.PROJECT_ID);

        this.getProjectMembers();
    }

    deleteMember(projectMember: User): void {
        this.projectService
            .deleteMemberFromProject(projectMember.username, this.projectId)
            .pipe(take(1))
            .subscribe(() => this.getProjectMembers());
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
                    .updateProjectName(newProjectName, this.projectId)
                    .pipe(take(1))
                    .subscribe((project: Project) => {
                        this.storage.saveProject(project);

                        this.projectName = project.name;
                    });
            });
    }

    deleteProject(): void {
        // TODO: add deleting project after adding dialogs
    }

    addNewMember(): void {
        const dialogRef = this.dialog.open(AddMemberDialogComponent, {
            enterAnimationDuration: '300ms',
            exitAnimationDuration: '100ms',
        });

        dialogRef
            .afterClosed()
            .pipe(take(1))
            .subscribe((username: string) => {
                if (username === null || username === "") {
                    return;
                }
                this.projectService
                    .addMemberToProject(username, this.projectId)
                    .pipe(take(1))
                    .subscribe((member: User) => {
                        this.pageNumber = 0;

                        this.getProjectMembers();
                    });
            });
    }

    leaveProject(): void {
        this.projectService
            .deleteMemberFromProject(this.currentUser.email as string, this.projectId)
            .pipe(take(1))
            .subscribe(() => this.router.navigate([RouterPaths.PROJECT_LIST_DIRECT_PATH]));
    }

    private getProjectMembers(): void {
        this.projectService
            .getProjectMembersOnPage(this.pageNumber, this.projectId)
            .pipe(take(1))
            .subscribe(membersList => {
                this.dataSource = new MatTableDataSource<User>(membersList.projectMembers);
                this.totalNumber = membersList.totalNumber;

                if (membersList.projectMembers.length === 20) {
                    this.pageNumber++;
                }
            });
    }
}
