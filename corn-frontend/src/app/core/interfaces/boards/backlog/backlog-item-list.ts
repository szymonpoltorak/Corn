import { BacklogItem } from "@interfaces/boards/backlog/backlog-item";

export interface BacklogItemList {
    backlogItemResponseList: BacklogItem[],
    totalNumber: number
}