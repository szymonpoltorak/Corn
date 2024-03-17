import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Sprint } from "@interfaces/boards/backlog/sprint";
import { ApiUrl } from "@core/enum/api-url";

@Injectable({
    providedIn: 'root'
})
export class SprintService {

    constructor(private http: HttpClient) {

    }

    getSprintsOnPageForProject(projectId: number, pageNumber: number): Observable<Sprint[]> {
        return this.http.get<Sprint[]>(`${ ApiUrl.GET_SPRINTS_ON_PAGE }`, {
            params: {
                page: pageNumber,
                projectId: projectId
            }
        });
    }
}