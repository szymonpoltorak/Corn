import { User } from "@core/interfaces/boards/user";

export interface BacklogItemResponse {
    backlogItemId: number;
    title: string;
    description: string;
    status: string;
    assignee: User;
    itemType: string;
    taskFinishDate: Date;
    projectId: number;
    sprintId: number;
}