import { BacklogItemResponse } from "./backlog-item-response.interface";

export interface BacklogItemResponseList {
    backlogItemResponseList: BacklogItemResponse[];
    totalNumber: number;
}