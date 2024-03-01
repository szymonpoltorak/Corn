import { AfterViewInit, Component, Input, OnDestroy, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskListComponent } from '../task_list/task_list.component';
import { SliceService } from './slice.service';
import { SliceDescriptor } from './slice_descriptor';
import { ColumnSetLayout } from '../layout.component';
import { SlicesModelService } from './slices_model.service';
import { CdkDropList } from '@angular/cdk/drag-drop';

@Component({
    selector: 'slice',
    standalone: true,
    imports: [
        CommonModule,
        TaskListComponent,
        ColumnSetLayout,
    ],
    templateUrl: './slice.component.html',
})
export class SliceComponent implements AfterViewInit, OnDestroy {

    @Input() sliceDescriptor?: SliceDescriptor;

    @ViewChild('todoTasklist') private todoTasklist!: TaskListComponent;
    @ViewChild('inprogressTasklist') private inprogressTasklist!: TaskListComponent;
    @ViewChild('doneTasklist') private doneTasklist!: TaskListComponent;

    constructor(
        protected readonly sliceService: SliceService,
        protected readonly sliceModelService: SlicesModelService,
    ) { }

    ngAfterViewInit(): void {
        this.sliceService.register(this);
    }

    ngOnDestroy(): void {
        this.sliceService.unregister(this);
    }

    setSiblingDroplistInstances(instances: CdkDropList[]) {
        if (this.todoTasklist && this.inprogressTasklist && this.doneTasklist) {
            this.todoTasklist.siblingDroplistInstances = instances;
            this.inprogressTasklist.siblingDroplistInstances = instances;
            this.doneTasklist.siblingDroplistInstances = instances;
        }
    }

    getLocalDroplistInstances(): CdkDropList[] {
        return this.todoTasklist && this.inprogressTasklist && this.doneTasklist ? [
            this.todoTasklist.getDroplistInstance(),
            this.inprogressTasklist.getDroplistInstance(),
            this.doneTasklist.getDroplistInstance(),
        ] : [];
    }

}
