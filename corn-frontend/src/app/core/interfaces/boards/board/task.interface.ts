import { Assignee } from "./assignee.interface";

export interface Task {
    associatedBacklogItemId: number;
    taskTag: string;
    content: string;
    assignee: Assignee
}
