import { BacklogItemCommentResponse } from "./backlog-item-comment-response.interface";

export interface BacklogItemCommentResponseList {
    comments: BacklogItemCommentResponse[];
    totalNumber: number;
}
