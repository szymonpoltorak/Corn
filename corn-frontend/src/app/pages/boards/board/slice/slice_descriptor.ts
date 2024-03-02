import { Task } from "@core/interfaces/boards/board/task.interface";

export interface ISliceDescriptor {
    todo: Task[];
    inprogress: Task[];
    done: Task[];
}

export class SliceDescriptor implements ISliceDescriptor {

    todo: Task[] = [];
    inprogress: Task[] = [];
    done: Task[] = [];
    metadata: any;

    constructor(metadata: any, sd?: ISliceDescriptor) {
        if(metadata) {
            this.metadata = metadata;
        }
        if (sd) {
            this.todo = [...sd.todo];
            this.inprogress = [...sd.inprogress];
            this.done = [...sd.done];
        }
    }

    size(): number {
        return this.todo.length + this.inprogress.length + this.done.length;
    }

    getAsFlattened(): Task[] {
        return [...this.todo, ...this.inprogress, ...this.done];
    }

    setMetadata(medatada: any): this {
        this.metadata = medatada;
        return this;
    }

    newFromSubset(taskSubset: Task[]): SliceDescriptor {
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
