import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Project } from "@interfaces/boards/project";
import { Observable } from "rxjs";

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
}