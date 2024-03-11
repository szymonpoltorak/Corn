import {BacklogItemStatus} from "@core/enum/BacklogItemStatus";
import {BacklogItemType} from "@core/enum/BacklogItemType";
import {User} from "@interfaces/boards/user";

export interface BacklogItem {
    id: number,
    title: string,
    description: string,
    status: BacklogItemStatus
    type: BacklogItemType
    assignee: User
}
