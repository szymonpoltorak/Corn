import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, take } from "rxjs";
import { SprintResponse } from "./data/sprint-response.interface";
import { SprintApiMappings } from "./sprint-api-mappings.enum";

@Injectable({
    providedIn: 'root'
})
export class SprintApi {

    constructor(private http: HttpClient) { }

    getCurrentAndFutureSprints(projectId: number): Observable<SprintResponse[]> {
        return this.http.get<SprintResponse[]>(SprintApiMappings.CURRENT_AND_FUTURE_SPRINTS, {
            params: {
                projectId: projectId,
            }
        }).pipe(take(1));
    }

}