import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { CdkDrag, CdkDragDrop, CdkDropList, } from '@angular/cdk/drag-drop';
import { CommonModule } from '@angular/common';
import { TaskCardComponent } from '@pages/boards/board/task-card/task-card.component';
import { Task } from '@core/interfaces/boards/board/task.interface';
import { TaskMovedByDnDEvent } from '@interfaces/boards/board/task-moved-by-dnd-event.interface';

@Component({
    selector: 'tasklist',
    standalone: true,
    imports: [
        CommonModule,
        CdkDropList,
        CdkDrag,
        TaskCardComponent,
    ],
    templateUrl: './task-list.component.html',
})
export class TaskListComponent {

    @Input() tasks: Task[] = [];
    @Input() siblingDroplistInstances: CdkDropList[] = [];

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