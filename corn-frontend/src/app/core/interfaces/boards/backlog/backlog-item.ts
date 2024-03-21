import { BacklogItemStatus } from "@core/enum/backlog-item-status";
import { BacklogItemType } from "@core/enum/backlog-item-type";
import { User } from "@interfaces/boards/user";

export interface BacklogItem {
    backlogItemId: number,
    title: string,
    description: string,
    status: BacklogItemStatus,
    itemType: BacklogItemType,
    assignee: User,
    sprintId: number,
    projectId: number
}
