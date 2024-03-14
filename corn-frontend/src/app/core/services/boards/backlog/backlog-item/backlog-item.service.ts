import { map } from 'rxjs/operators';
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { BacklogItem } from "@interfaces/boards/backlog/backlog.item";
import { environment } from "@environments/environment";
import { ApiUrl } from "@core/enum/api-url";
import { BacklogItemStatus } from "@core/enum/BacklogItemStatus";
import { BacklogItemType } from "@core/enum/BacklogItemType";
import { UserService } from "@core/services/users/user.service";
import { BacklogItemList } from "@interfaces/boards/backlog/backlog.item.list";

@Injectable({
    providedIn: 'root'
})
export class BacklogItemService {

    constructor(private http: HttpClient,
                private userService: UserService) {
    }

    getAllByProjectId(projectId: number, pageNumber: number, sortBy: string, order: string): Observable<BacklogItemList> {
        return this.http.get<any>(`${ environment.httpBackend }${ ApiUrl.GET_BACKLOG_ITEMS_BY_PROJECT_ID }`, {
            params: {
                projectId: projectId,
                pageNumber: pageNumber,
                sortBy: sortBy,
                order: order
            }
        })
            .pipe(
                map((response: any) => {
                    const backlogItems: BacklogItem[] = response.backlogItemResponseList.map((item: BacklogItem) => this.transformToBacklogItem(item));
                    const totalNumber: number = response.totalNumber;
                    return { backlogItems: backlogItems, totalNumber: totalNumber };
                })
            );
    }

    createNewBacklogItem(title: string, description: string, projectMemberId: number, sprintId: number,
                         projectId: number, itemType: BacklogItemType): Observable<BacklogItem> {
        return this.http.post<BacklogItem>(`${ ApiUrl.CREATE_BACKLOG_ITEM }`, {
            title: title,
            description: description,
            projectMemberId: projectMemberId,
            sprintId: sprintId,
            projectId: projectId,
            itemType: this.getItemType(itemType),
        })
    }

    updateBacklogItem(item: BacklogItem): Observable<BacklogItem> {
        return this.http.put<BacklogItem>(`${ApiUrl.UPDATE_BACKLOG_ITEM}`, {
            title: item.title,
            description: item.description,
            projectMemberId: item.assignee.userId,
            sprintId: item.sprintId,
            projectId: item.projectId,
            itemType: this.getItemType(item.type),
            itemStatus: this.getStatus(item.status)
        }, {
            params: {
                id: item.id
            }
        })
    }

    deleteBacklogItem(item: BacklogItem): Observable<BacklogItem> {
        return this.http.delete<BacklogItem>(`${ApiUrl.DELETE_BACKLOG_ITEM}`, {
            params: {
                id: item.id
            }
        })
    }


    private transformToBacklogItem(item: any): BacklogItem {
        let status: BacklogItemStatus = (() => {
            switch (item.status) {
                case "TODO":
                    return BacklogItemStatus.TODO;
                case "IN_PROGRESS":
                    return BacklogItemStatus.IN_PROGRESS;
                default:
                    return BacklogItemStatus.DONE;
            }
        })();

        let type: BacklogItemType = (() => {
            switch (item.itemType) {
                case "STORY":
                    return BacklogItemType.STORY;
                case "BUG":
                    return BacklogItemType.BUG;
                case "EPIC":
                    return BacklogItemType.EPIC;
                default:
                    return BacklogItemType.TASK;
            }
        })();

        return {
            id: item.backlogItemId,
            title: item.title,
            description: item.description,
            status: status,
            type: type,
            assignee: this.userService.mapToUser(item.assignee),
            projectId: item.projectId,
            sprintId: item.sprintId
        };
    }

    private getItemType(itemType: BacklogItemType): string {
        switch (itemType) {
            case BacklogItemType.STORY:
                return "STORY";
            case BacklogItemType.BUG:
                return "BUG";
            case BacklogItemType.EPIC:
                return "EPIC";
            default:
                return "TASK";
        }
    }

    private getStatus(itemStatus: BacklogItemStatus) : string {
        switch (itemStatus) {
            case BacklogItemStatus.TODO:
                return "TODO";
            case BacklogItemStatus.IN_PROGRESS:
                return "IN_PROGRESS";
            default:
                return "DONE";
        }
    }
}