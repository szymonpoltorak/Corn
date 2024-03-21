import { Task } from "@core/interfaces/boards/board/task.interface";
import { GroupInfo } from "@interfaces/boards/board/group-info.interface";

export type TaskGrouper<T> = (ungrouped: Task[]) => GroupInfo<T>[];