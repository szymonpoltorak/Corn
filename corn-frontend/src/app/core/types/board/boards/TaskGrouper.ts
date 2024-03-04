import { Task } from "@core/interfaces/boards/board/task.interface";
import { GroupInfo } from "@core/interfaces/boards/board/group_info.interface";

export type TaskGrouper<T> = (ungrouped: Task[]) => GroupInfo<T>[];