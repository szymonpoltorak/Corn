import { Task } from "@core/interfaces/boards/board/task.interface";

export interface GroupInfo<T> { group: Task[], metadata: T };