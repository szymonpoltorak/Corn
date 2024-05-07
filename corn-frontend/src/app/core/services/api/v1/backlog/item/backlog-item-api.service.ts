import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, take } from "rxjs";
import { BacklogItemResponse } from "./data/backlog-item-response.interface";
import { BacklogItemRequest } from "./data/backlog-item-request.interface";
import { BacklogItemResponseList } from "./data/backlog-item-response-list.interface";
import { BacklogItemDetails } from "./data/backlog-item-details.interface";
import { BacklogItemApiMappings } from "./backlog-item-api-mappings.enum";
import { BacklogItemRequestPartialUpdate } from "./data/backlog-item-request-partial-update.interface";

@Injectable({
    providedIn: 'root'
})
export class BacklogItemApiService {

    constructor(private http: HttpClient) { }

    getById(id: number): Observable<BacklogItemResponse> {
        return this.http.get<BacklogItemResponse>(BacklogItemApiMappings.GET, {
            params: {
                id: id,
            }
        }).pipe(take(1));
    }

    update(id: number, backlogItemRequest: BacklogItemRequest): Observable<BacklogItemResponse> {
        return this.http.put<BacklogItemResponse>(BacklogItemApiMappings.UPDATE, backlogItemRequest, {
            params: {
                id: id,
            }
        }).pipe(take(1));
    }

    deleteById(id: number): Observable<BacklogItemResponse> {
        return this.http.delete<BacklogItemResponse>(BacklogItemApiMappings.DELETE, {
            params: {
                id: id,
            }
        }).pipe(take(1));
    }

    create(backlogItemRequest: BacklogItemRequest): Observable<BacklogItemResponse> {
        return this.http.post<BacklogItemResponse>(BacklogItemApiMappings.ADD, backlogItemRequest).pipe(take(1));
    }

    getBySprintId(sprintId: number, pageNumber: number, sortBy: string, order: string): Observable<BacklogItemResponseList> {
        return this.http.get<BacklogItemResponseList>(BacklogItemApiMappings.GET_BY_SPRINT, {
            params: {
                sprintId: sprintId,
                pageNumber: pageNumber,
                sortBy: sortBy,
                order: order,
            }
        }).pipe(take(1));
    }

    getByProjectId(projectId: number, pageNumber: number, sortBy: string, order: string): Observable<BacklogItemResponseList> {
        return this.http.get<BacklogItemResponseList>(BacklogItemApiMappings.GET_BY_PROJECT, {
            params: {
                projectId: projectId,
                pageNumber: pageNumber,
                sortBy: sortBy,
                order: order,
            }
        }).pipe(take(1));
    }

    getDetailsById(id: number): Observable<BacklogItemDetails> {
        return this.http.get<BacklogItemDetails>(BacklogItemApiMappings.GET_DETAILS, {
            params: {
                id: id,
            }
        }).pipe(take(1));
    }

    getAllWithoutSprint(projectId: number, pageNumber: number, sortBy: string, order: string): Observable<BacklogItemResponseList> {
        return this.http.get<BacklogItemResponseList>(BacklogItemApiMappings.GET_ALL_WITHOUT_SPRINT, {
            params: {
                projectId: projectId,
                pageNumber: pageNumber,
                sortBy: sortBy,
                order: order,
            }
        }).pipe(take(1));
    }

    getAllBySprintId(sprintId: number): Observable<BacklogItemResponse[]> {
        return this.http.get<BacklogItemResponse[]>(BacklogItemApiMappings.BACKLOG_ITEM_GET_ALL_BY_SPRINT_MAPPING, {
            params: {
                sprintId: sprintId,
            }
        }).pipe(take(1));
    }

    partialUpdate(id: number, BacklogItemRequestPartialUpdate: BacklogItemRequestPartialUpdate): Observable<BacklogItemResponse> {
        return this.http.patch<BacklogItemResponse>(BacklogItemApiMappings.PARTIAL_UPDATE, BacklogItemRequestPartialUpdate, {
            params: {
                id: id,
            }
        }).pipe(take(1));
    }

}