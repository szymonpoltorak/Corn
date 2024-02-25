import { AfterViewInit, Component, Input, OnDestroy, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TasklistComponent } from '../tasklist/tasklist.component';
import { SliceService } from './slice.service';
import { SliceDescriptor } from './slice_descriptor';
import { ColumnSetLayout } from '../layout.component';
import { SlicesModelService } from './slices_model.service';

@Component({
    selector: 'slice',
    standalone: true,
    imports: [
        CommonModule,
        TasklistComponent,
        ColumnSetLayout,
    ],
    templateUrl: './slice.component.html',
})
export class SliceComponent implements AfterViewInit, OnDestroy {

    @Input() sliceDescriptor?: SliceDescriptor;

    @ViewChild('todoTasklist') private todoTasklist!: TasklistComponent;
    @ViewChild('inprogressTasklist') private inprogressTasklist!: TasklistComponent;
    @ViewChild('doneTasklist') private doneTasklist!: TasklistComponent;

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

    setSiblingDroplistInstances(instances: any[]) {
        if (this.todoTasklist && this.inprogressTasklist && this.doneTasklist) {
            this.todoTasklist.siblingDroplistInstances = instances;
            this.inprogressTasklist.siblingDroplistInstances = instances;
            this.doneTasklist.siblingDroplistInstances = instances;
        }
    }

    getLocalDroplistInstances(): any[] {
        return this.todoTasklist && this.inprogressTasklist && this.doneTasklist ? [
            this.todoTasklist.getDroplistInstance(),
            this.inprogressTasklist.getDroplistInstance(),
            this.doneTasklist.getDroplistInstance(),
        ] : [];
    }

}
