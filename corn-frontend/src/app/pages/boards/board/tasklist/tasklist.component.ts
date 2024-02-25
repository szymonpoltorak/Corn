import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { CdkDrag, CdkDragDrop, CdkDropList, } from '@angular/cdk/drag-drop';
import { CommonModule } from '@angular/common';
import { TaskcardComponent } from '../taskcard/taskcard.component';
import { Task } from '../model/model';
import { TaskMovedByDnDEvent } from '../slice/slices_model.service';

@Component({
    selector: 'tasklist',
    standalone: true,
    imports: [
        CommonModule,
        CdkDropList,
        CdkDrag,
        TaskcardComponent,
    ],
    templateUrl: './tasklist.component.html',
})
export class TasklistComponent {

    @Input() tasks: Task[] = [];
    @Input() siblingDroplistInstances: any[] = [];

    @Output() taskMoved: EventEmitter<TaskMovedByDnDEvent> = new EventEmitter();

    @ViewChild('droplistInstance') private droplistInstance!: CdkDropList;

    getDroplistInstance() {
        return this.droplistInstance;
    }

    protected droppedHandler(event: CdkDragDrop<Task[]>) {
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