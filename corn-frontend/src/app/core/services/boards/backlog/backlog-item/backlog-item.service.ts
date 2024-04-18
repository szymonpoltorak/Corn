import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { BacklogItem } from "@interfaces/boards/backlog/backlog.item";
import { ApiUrl } from "@core/enum/api-url";
import { BacklogItemType } from "@core/enum/BacklogItemType";
import { BacklogItemList } from "@interfaces/boards/backlog/backlog.item.list";

@Injectable({
    providedIn: 'root'
})
export class BacklogItemService {

    constructor(private http: HttpClient) {
    }

    getAllByProjectId(projectId: number, pageNumber: number, sortBy: string, order: string): Observable<BacklogItemList> {
        return this.http.get<BacklogItemList>(ApiUrl.GET_BACKLOG_ITEMS_BY_PROJECT_ID, {
            params: {
                projectId: projectId,
                pageNumber: pageNumber,
                sortBy: sortBy,
                order: order
            }
        });
    }

    getAllBySprintId(sprintId: number, pageNumber: number, sortBy: string, order: string): Observable<BacklogItemList> {
        return this.http.get<BacklogItemList>(ApiUrl.GET_BACKLOG_ITEMS_BY_SPRINT_ID, {
            params: {
                sprintId: sprintId,
                pageNumber: pageNumber,
                sortBy: sortBy,
                order: order
            }
        });
    }

    getAllWithoutSprint(projectId: number, pageNumber: number, sortBy: string, order: string): Observable<BacklogItemList> {
        return this.http.get<BacklogItemList>(ApiUrl.GET_BACKLOG_ITEMS_WITHOUT_SPRINT, {
            params: {
                projectId: projectId,
                pageNumber: pageNumber,
                sortBy: sortBy,
                order: order
            }
        });
    }

    createNewBacklogItem(title: string, description: string, projectMemberId: number, sprintId: number,
                         projectId: number, itemType: BacklogItemType): Observable<BacklogItem> {
        return this.http.post<BacklogItem>(ApiUrl.CREATE_BACKLOG_ITEM, {
            title: title,
            description: description,
            projectMemberId: projectMemberId,
            sprintId: sprintId,
            projectId: projectId,
            itemType: itemType.toString()
        })
    }

    updateBacklogItem(item: BacklogItem): Observable<BacklogItem> {
        return this.http.put<BacklogItem>(ApiUrl.UPDATE_BACKLOG_ITEM, {
            title: item.title,
            description: item.description,
            projectMemberId: item.assignee ? item.assignee.userId : -1,
            sprintId: item.sprintId,
            projectId: item.projectId,
            itemType: item.itemType.toString(),
            itemStatus: item.status.toString()
        }, {
            params: {
                id: item.backlogItemId
            }
        })
    }

    deleteBacklogItem(item: BacklogItem): Observable<BacklogItem> {
        return this.http.delete<BacklogItem>(ApiUrl.DELETE_BACKLOG_ITEM, {
            params: {
                id: item.backlogItemId
            }
        })
    }
}