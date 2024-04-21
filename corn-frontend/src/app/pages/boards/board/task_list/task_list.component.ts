import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { CdkDrag, CdkDragDrop, CdkDropList, } from '@angular/cdk/drag-drop';
import { CommonModule } from '@angular/common';
import { TaskCardComponent } from '../task_card/task_card.component';
import { Task } from '@core/interfaces/boards/board/task.interface';
import { TaskMovedByDnDEvent } from '@core/interfaces/boards/board/task_moved_by_dnd_event.interface';
import { TaskChangedGroupEvent } from '@core/interfaces/boards/board/task_changed_group_event.interface';
import { Assignee } from '@core/interfaces/boards/board/assignee.interface';

@Component({
    selector: 'tasklist',
    standalone: true,
    imports: [
        CommonModule,
        CdkDropList,
        CdkDrag,
        TaskCardComponent,
    ],
    templateUrl: './task_list.component.html',
})
export class TaskListComponent {

    @Input() tasks: Task[] = [];
    @Input() siblingDroplistInstances: CdkDropList[] = [];

    @Input() assigneeChangedHandler?: (event: TaskChangedGroupEvent<Assignee>) => void;

    @Output() taskMoved: EventEmitter<TaskMovedByDnDEvent> = new EventEmitter();

    @ViewChild('droplistInstance') private droplistInstance!: CdkDropList;

    getDroplistInstance(): CdkDropList {
        return this.droplistInstance;
    }

    protected droppedHandler(event: CdkDragDrop<Task[]>): void {
        if (event.previousContainer === event.container && event.previousIndex === event.currentIndex) {
            return;
        }
        this.taskMoved.emit({
            sourceArray: event.previousContainer.data,
            sourceIndex: event.previousIndex,
            destinationArray: event.container.data,
            destinationIndex: event.currentIndex,
        });
    }

}