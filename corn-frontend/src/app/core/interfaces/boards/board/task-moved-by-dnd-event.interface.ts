import { Task } from "./task.interface";

export interface TaskMovedByDnDEvent {
    sourceArray: Task[],
    sourceIndex: number,
    destinationArray: Task[],
    destinationIndex: number,
}