import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Sprint } from "@interfaces/boards/backlog/sprint";
import { ApiUrl } from "@core/enum/api-url";
import { StorageService } from "@core/services/storage.service";
import { StorageKey } from "@core/enum/storage-key.enum";
import { Moment } from "moment";

@Injectable({
    providedIn: 'root'
})
export class SprintService {

    constructor(private http: HttpClient,
                private storage: StorageService) {

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

    getSprintsBetweenDates(startDate: Moment, endDate: Moment): Observable<Sprint[]> {
        return this.http.get<Sprint[]>(ApiUrl.GET_SPRINTS_BETWEEN_DATES, {
            params: {
                startDate: startDate.format('YYYY-MM-DD'),
                endDate: endDate.format('YYYY-MM-DD'),
                projectId: this.storage.getValueFromStorage(StorageKey.PROJECT_ID)
            }
        });
    }
}