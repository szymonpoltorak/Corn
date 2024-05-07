import { Injectable, Input } from '@angular/core';
import { moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { Assignee } from '@core/interfaces/boards/board/assignee.interface';
import { Task } from '@core/interfaces/boards/board/task.interface';

@Injectable()
export class BoardModelService {

    assignees: Assignee[] = [];
    todo: Task[] = [];
    inprogress: Task[] = [];
    done: Task[] = [];

    @Input() modelChangeHandler?: () => void;

    setAssigneeForTask(task: Task, assignee?: Assignee) {
        task.assignee = assignee;
        this.modelChangeHandler && this.modelChangeHandler();
    }

    moveTaskToArray(task: Task, sourceColumn: Task[], sourceColumnIndex: number, destinationColumn: Task[], destinationColumnIndex: number) {
        const index = destinationColumn.indexOf(task);
        if (index == destinationColumnIndex)
            return;
        if (destinationColumnIndex >= 0 && sourceColumn == destinationColumn) {
            moveItemInArray(sourceColumn, sourceColumnIndex, destinationColumnIndex);
        } else {
            transferArrayItem(sourceColumn, destinationColumn, sourceColumnIndex, destinationColumnIndex);
        }
        this.modelChangeHandler && this.modelChangeHandler();
    }

    todoSize(): number {
        return this.todo.length;
    }

    inprogressSize(): number {
        return this.inprogress.length;
    }

    doneSize(): number {
        return this.done.length;
    }

    assigneesSize(): number {
        return this.assignees.length;
    }

    allTasksSize(): number {
        return this.todo.length + this.inprogress.length + this.done.length;
    }

}
