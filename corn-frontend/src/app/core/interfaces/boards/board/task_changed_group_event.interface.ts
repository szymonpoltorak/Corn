import { Task } from "./task.interface";

export interface TaskChangedGroupEvent<T> {
    task: Task,
    sourceGroupMetadata: T,
    destinationGroupMetadata: T,
}