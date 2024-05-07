import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, take } from "rxjs";
import { User } from "@core/interfaces/boards/user";
import { ProjectMemberInfoExtendedResponse } from "./data/project-member-info-extended-reponse.interface";
import { ProjectMemberApiMappings } from "./project-member-api-mappings.enum";

@Injectable({
    providedIn: 'root'
})
export class ProjectMemberApiService {

    constructor(private http: HttpClient) { }

    addMemberToProject(username: string, projectId: number): Observable<User> {
        return this.http.post<User>(ProjectMemberApiMappings.ADD_MEMBER_TO_PROJECT, {
            params: {
                username: username,
                projectId: projectId,
            }
        }).pipe(take(1));
    }

    getProjectMembers(projectId: number, page: number): Observable<User[]> {
        return this.http.get<User[]>(ProjectMemberApiMappings.GET_MEMBERS_OF_PROJECT, {
            params: {
                projectId: projectId,
                page: page,
            }
        }).pipe(take(1));
    }

    removeMemberFromProject(username: string, projectId: number): Observable<User> {
        return this.http.delete<User>(ProjectMemberApiMappings.REMOVE_MEMBER_FROM_PROJECT, {
            params: {
                username: username,
                projectId: projectId,
            }
        }).pipe(take(1));
    }

    getProjectMemberId(projectId: number): Observable<ProjectMemberInfoExtendedResponse> {
        return this.http.get<ProjectMemberInfoExtendedResponse>(ProjectMemberApiMappings.GET_PROJECT_MEMBER_ID, {
            params: {
                projectId: projectId,
            }
        }).pipe(take(1));
    }

    getAllProjectMembers(projectId: number): Observable<ProjectMemberInfoExtendedResponse[]> {
        return this.http.get<ProjectMemberInfoExtendedResponse[]>(ProjectMemberApiMappings.GET_ALL_MEMBERS_OF_PROJECT, {
            params: {
                projectId: projectId,
            }
        }).pipe(take(1));
    }
}