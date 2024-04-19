import { User } from "@core/interfaces/boards/user";
import { SprintResponse } from "../../../sprint/data/sprint-response.interface";
import { ProjectResponse } from "../../../project/data/project-response.interface";
import { BacklogItemCommentResponse } from "../comment/data/backlog-item-comment-response.interface";

export interface BacklogItemDetails {
    comments: BacklogItemCommentResponse[];
    assignee: User;
    sprint: SprintResponse;
    project: ProjectResponse;
}