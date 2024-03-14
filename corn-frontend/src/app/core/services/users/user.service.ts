import { Injectable } from '@angular/core';
import { User } from "@interfaces/boards/user";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { ApiUrl } from "@core/enum/api-url";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient) {
    }

    getProjectMembersOnPage(projectId: number, page: number): Observable<User[]> {
        return this.http.get<User[]>(`${ApiUrl.GET_PROJECT_MEMBERS}`, {
            params: {
                projectId: projectId,
                page: page
            }
        })
    }
}
