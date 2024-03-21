import { Task } from "@core/interfaces/boards/board/task.interface";

export interface SliceDescriptorDataHolder {
    todo: Task[];
    inprogress: Task[];
    done: Task[];
}