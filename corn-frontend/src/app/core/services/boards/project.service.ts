import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Project } from "@interfaces/boards/project";
import { Observable } from "rxjs";
import { User } from "@interfaces/boards/user";
import { ProjectMemberList } from "@interfaces/boards/project-member-list.interface";

@Injectable({
    providedIn: 'root'
})
export class ProjectService {

    constructor(private http: HttpClient) {
    }

    getProjectsOnPage(pageNumber: number): Observable<Project[]> {
        return this.http.get<Project[]>('/api/v1/project/getProjectsOnPage', {
            params: {
                page: pageNumber
            }
        });
    }

    createNewProject(newProjectName: string): Observable<Project> {
        return this.http.post<Project>("/api/v1/project/addProject", {}, {
            params: {
                name: newProjectName
            }
        });
    }

    updateProjectName(newProjectName: string, projectId: number): Observable<Project> {
        return this.http.put<Project>("/api/v1/project/updateProjectsName", {}, {
            params: {
                name: newProjectName,
                projectId: projectId
            }
        });
    }

    deleteMemberFromProject(username: string, projectId: number): Observable<User> {
        return this.http.delete<User>("/api/v1/project/member/removeMember", {
            params: {
                username: username,
                projectId: projectId
            }
        });
    }

    getProjectMembersOnPage(pageNumber: number, projectId: number): Observable<ProjectMemberList> {
        return this.http.get<ProjectMemberList>("/api/v1/project/member/getMembers", {
            params: {
                page: pageNumber,
                projectId: projectId
            }
        });
    }
}