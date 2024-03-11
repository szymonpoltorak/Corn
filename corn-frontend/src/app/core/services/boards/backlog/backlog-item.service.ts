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
        return this.http.get<any>(`${environment.httpBackend}${ApiUrl.GET_BACKLOG_ITEMS_BY_PROJECT_ID}`, {params: {
            projectId: projectId,
            pageNumber: pageNumber,
            sortBy: sortBy,
            order: order}})
            .pipe(
                map((response: any) => {
                    const backlogItems: BacklogItem[] = response.backlogItemResponseList.map((item : BacklogItem) => this.transformToBacklogItem(item));
                    const totalNumber: number = response.totalNumber;
                    return { backlogItems: backlogItems, totalNumber: totalNumber};
                })
            );
    }

    private transformToBacklogItem(item: any): BacklogItem {
        let status: BacklogItemStatus = (() => {
            switch(item.status) {
                case "TODO":
                    return BacklogItemStatus.TODO;
                case "IN_PROGRESS":
                    return BacklogItemStatus.IN_PROGRESS;
                default:
                    return BacklogItemStatus.DONE;
            }
        })();

        let type: BacklogItemType = (() => {
            switch(item.itemType) {
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
            id: item.id,
            title: item.title,
            description: item.description,
            status: status,
            type: type,
            assignee: this.userService.mapToUser(item.assignee)
        };
    }
}