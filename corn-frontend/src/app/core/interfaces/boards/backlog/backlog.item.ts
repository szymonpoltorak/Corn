import { BacklogItemStatus } from "@core/enum/BacklogItemStatus";
import { BacklogItemType } from "@core/enum/BacklogItemType";
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
