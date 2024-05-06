import { BacklogItemType } from "@core/enum/BacklogItemType";
import { Assignee } from "./assignee.interface";

export interface Task {
    associatedBacklogItemId: number;
    taskTag: string;
    content: string;
    assignee: Assignee;
    taskType: BacklogItemType;
}
