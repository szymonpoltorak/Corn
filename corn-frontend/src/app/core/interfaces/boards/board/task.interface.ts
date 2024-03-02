import { Assignee } from "./assignee.interface";

export interface Task {
    taskid: string;
    content: string;
    assignee: Assignee
}
