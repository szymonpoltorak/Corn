import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, take } from "rxjs";
import { SprintResponse } from "./data/sprint-response.interface";
import { SprintApiMappings } from "./sprint-api-mappings.enum";
import { Page } from "@core/services/api/utils/page.interface";
import { Pageable } from "@core/services/api/utils/pageable.interface";

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

    getSprintsAfterSprint(sprintId: number, pageable?: Pageable<SprintResponse>): Observable<Page<SprintResponse>> {
        return this.http.get<Page<SprintResponse>>(SprintApiMappings.SPRINTS_AFTER_SPRINT, {
            params: {
                sprintId: sprintId,
                ...(pageable != undefined ? pageable as {} : {})
            }
        }).pipe(take(1));
    }

    getSprintsBeforeSprint(sprintId: number, pageable?: Pageable<SprintResponse>): Observable<Page<SprintResponse>> {
        return this.http.get<Page<SprintResponse>>(SprintApiMappings.SPRINTS_BEFORE_SPRINT, {
            params: {
                sprintId: sprintId,
                ...(pageable != undefined ? pageable as {} : {})
            }
        }).pipe(take(1));
    }

}