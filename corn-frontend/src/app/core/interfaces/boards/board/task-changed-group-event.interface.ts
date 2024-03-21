import { Task } from "./task.interface";

export interface TaskChangedGroupEvent {
    task: Task,
    sourceGroupMetadata: any,
    destinationGroupMetadata: any,
}