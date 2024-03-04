import { SliceDescriptorDataHolder } from "@core/interfaces/boards/board/slice/slice_descriptor_data_holder.interface";
import { Task } from "@core/interfaces/boards/board/task.interface";

export class SliceDescriptor<T> implements SliceDescriptorDataHolder {

    todo: Task[] = [];
    inprogress: Task[] = [];
    done: Task[] = [];
    metadata: any;

    constructor(metadata: any, dataHolder?: SliceDescriptorDataHolder) {
        if(metadata) {
            this.metadata = metadata;
        }
        if (dataHolder) {
            this.todo = [...dataHolder.todo];
            this.inprogress = [...dataHolder.inprogress];
            this.done = [...dataHolder.done];
        }
    }

    size(): number {
        return this.todo.length + this.inprogress.length + this.done.length;
    }

    getAsFlattened(): Task[] {
        return [...this.todo, ...this.inprogress, ...this.done];
    }

    setMetadata(medatada: T): this {
        this.metadata = medatada;
        return this;
    }

    newFromSubset(taskSubset: Task[]): SliceDescriptor<T> {
        const result = new SliceDescriptor(null, undefined);
        for (let i = 0; i < taskSubset.length; i++) {
            if (this.todo.indexOf(taskSubset[i]) >= 0) {
                result.todo.push(taskSubset[i]);
            } else if (this.inprogress.indexOf(taskSubset[i]) >= 0) {
                result.inprogress.push(taskSubset[i]);
            } else if (this.done.indexOf(taskSubset[i]) >= 0) {
                result.done.push(taskSubset[i]);
            } else {
                break;
            }
        }
        return result;
    }

}
