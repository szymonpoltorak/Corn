import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Sprint } from "@interfaces/boards/backlog/sprint";
import { ApiUrl } from "@core/enum/api-url";
import { StorageService } from "@core/services/storage.service";

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
                projectId: this.storage.getProjectId()
            }
        });
    }

    getCurrentAndFutureSprints(): Observable<Sprint[]> {
        return this.http.get<Sprint[]>(ApiUrl.GET_CURRENT_AND_FUTURE_SPRINTS, {
            params: {
                projectId: this.storage.getProjectId()
            }
        });
    }
}