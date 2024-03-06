import { Task } from "./task.interface";

export interface TaskChangedColumnEvent {
    task: Task,
    sourceColumn: Task[],
    sourceColumnIndex: number,
    destinationColumn: Task[],
    destinationColumnIndex: number,
}