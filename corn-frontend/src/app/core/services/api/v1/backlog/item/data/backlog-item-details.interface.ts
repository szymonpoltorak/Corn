import { User } from "@core/interfaces/boards/user";
import { SprintResponse } from "@core/services/api/v1/sprint/data/sprint-response.interface";
import { ProjectResponse } from "@core/services/api/v1/project/data/project-response.interface";
import { BacklogItemCommentResponse } from "../comment/data/backlog-item-comment-response.interface";

export interface BacklogItemDetails {
    comments: BacklogItemCommentResponse[];
    assignee: User;
    sprint: SprintResponse;
    project: ProjectResponse;
}