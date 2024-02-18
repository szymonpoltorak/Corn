import {BacklogItemStatus} from "@core/enum/backlog-item-status";

export interface BacklogItem {
    title: string,
    description: string,
    status: BacklogItemStatus
}
