import {BacklogItem} from "@interfaces/boards/backlog/backlog.item";

export interface BacklogItemList {
    backlogItems: BacklogItem[],
    totalNumber: number
}