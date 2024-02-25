import { Injectable, Input } from '@angular/core';
import { Assignee, Task } from './model';
import { moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';

@Injectable()
export class ModelService {

    sprintName: string = 'Sprint 1';
    assignees: Assignee[] = [];
    todo: Task[] = [];
    inprogress: Task[] = [];
    done: Task[] = [];

    @Input() modelChangeHandler?: () => void;

    setAssigneeForTask(task: Task, assignee: Assignee) {
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

    todoSize() {
        return this.todo.length;
    }

    inprogressSize() {
        return this.inprogress.length;
    }

    doneSize() {
        return this.done.length;
    }

    assigneesSize() {
        return this.assignees.length;
    }

    allTasksSize() {
        return this.todo.length + this.inprogress.length + this.done.length;
    }

}
