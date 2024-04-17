import { Injectable } from '@angular/core';
import { User } from "@interfaces/boards/user";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { ApiUrl } from "@core/enum/api-url";
import { StorageService } from "@core/services/storage.service";
import { StorageKey } from '@core/enum/storage-key.enum';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient,
                private storage: StorageService) {
    }

    getProjectMembersOnPage(page: number): Observable<User[]> {
        return this.http.get<User[]>(`${ApiUrl.GET_PROJECT_MEMBERS}`, {
            params: {
                projectId: this.storage.getValueFromStorage(StorageKey.PROJECT_ID),
                page: page
            }
        })
    }
}
