import { AfterViewInit, Component, Input, OnDestroy, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskListComponent } from '@pages/boards/board/task-list/task-list.component';
import { SliceService } from '@core/services/boards/board/slice/slice.service';
import { SliceDescriptor } from '@core/services/boards/board/slice/slice-descriptor';
import { SlicesModelService } from '@core/services/boards/board/slice/slices-model.service';
import { CdkDropList } from '@angular/cdk/drag-drop';
import { ColumnSetLayout } from '../layout/column-set-layout.component';

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
export class SliceComponent<T> implements AfterViewInit, OnDestroy {

    @Input() sliceDescriptor?: SliceDescriptor<T>;

    @ViewChild('todoTasklist') private todoTasklist!: TaskListComponent;
    @ViewChild('inprogressTasklist') private inprogressTasklist!: TaskListComponent;
    @ViewChild('doneTasklist') private doneTasklist!: TaskListComponent;

    constructor(
        private readonly sliceService: SliceService<T>,
        protected readonly sliceModelService: SlicesModelService<T>,
    ) { }

    ngAfterViewInit(): void {
        this.sliceService.register(this);
    }

    ngOnDestroy(): void {
        this.sliceService.unregister(this);
    }

    setSiblingDroplistInstances(instances: CdkDropList[]): void {
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
