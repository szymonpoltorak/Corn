import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Sprint } from "@interfaces/boards/backlog/sprint";
import { ApiUrl } from "@core/enum/api-url";
import { StorageService } from "@core/services/storage.service";
import { StorageKey } from "@core/enum/storage-key.enum";

@Injectable({
    providedIn: 'root'
})
export class SprintService {

    constructor(private http: HttpClient,
                private storage: StorageService) {

    }

    deleteSprint(sprintId: number): Observable<void> {
        return this.http.delete<void>(ApiUrl.DELETE_SPRINT, {
            params: {
                sprintId: sprintId
            }
        });

    }

    editSprintName(name: string, sprintId: number): Observable<void> {
        return this.http.put<void>(ApiUrl.UPDATE_SPRINTS_NAME, {}, {
            params: {
                name: name,
                sprintId: sprintId
            }
        });
    }

    editSprintStartDate(startDate: string, sprintId: number): Observable<void> {
        return this.http.put<void>(ApiUrl.UPDATE_SPRINTS_START_DATE, {}, {
            params: {
                startDate: startDate,
                sprintId: sprintId
            }
        });
    }

    editSprintEndDate(sprint: Sprint): Observable<void> {
        return this.http.put<void>(ApiUrl.UPDATE_SPRINTS_END_DATE, {}, {
            params: {
                endDate: sprint.endDate,
                sprintId: sprint.sprintId
            }
        });
    }

    editSprintDescription(description: string, sprintId: number): Observable<void> {
        return this.http.put<void>(ApiUrl.UPDATE_SPRINTS_DESCRIPTION, {}, {
            params: {
                description: description,
                sprintId: sprintId
            }
        });

    }

    getSprintsOnPageForProject(pageNumber: number): Observable<Sprint[]> {
        return this.http.get<Sprint[]>(ApiUrl.GET_SPRINTS_ON_PAGE, {
            params: {
                page: pageNumber,
                projectId: this.storage.getValueFromStorage(StorageKey.PROJECT_ID)
            }
        });
    }

    getCurrentAndFutureSprints(): Observable<Sprint[]> {
        return this.http.get<Sprint[]>(ApiUrl.GET_CURRENT_AND_FUTURE_SPRINTS, {
            params: {
                projectId: this.storage.getValueFromStorage(StorageKey.PROJECT_ID)
            }
        });
    }
}