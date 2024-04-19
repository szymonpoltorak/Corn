import { User } from "@core/interfaces/boards/user";

export interface BacklogItemCommentResponse {
    comment: string;
    user: User;
    commentTime: Date;
    lastEditTime: Date;
    backlogItemCommentId: number;
}