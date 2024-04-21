import { BacklogItemComment } from "@interfaces/boards/backlog/backlog-item-comment";

export interface BacklogItemCommentList {
    comments: BacklogItemComment[],
    totalNumber: number
}
