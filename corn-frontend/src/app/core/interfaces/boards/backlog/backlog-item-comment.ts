import { User } from "@interfaces/boards/user";

export interface BacklogItemComment {
    comment: string,
    user: User,
    commentTime: Date
    backlogItemCommentId: number,
    lastEditTime: Date,
    canEdit: boolean
}
