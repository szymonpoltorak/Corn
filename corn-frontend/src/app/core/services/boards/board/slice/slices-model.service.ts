import { Injectable, Input } from '@angular/core';
import { SliceDescriptor } from './slice-descriptor';
import { BoardModelService } from '../model.service';
import { Task } from '@core/interfaces/boards/board/task.interface';
import { TaskGrouper } from '@core/types/board/boards/task-grouper';
import { TaskChangedGroupEvent } from '@interfaces/boards/board/task-changed-group-event.interface';
import { TaskChangedColumnEvent } from '@interfaces/boards/board/task-changed-column-event.interface';
import { SliceInfo } from '@interfaces/boards/board/slice/slice-info.interface';
import { TaskMovedByDnDEvent } from '@interfaces/boards/board/task-moved-by-dnd-event.interface';

@Injectable()
export class SlicesModelService<T> {

    @Input() filter?: (task: Task) => boolean;
    @Input() grouper?: TaskGrouper<T>;

    @Input() groupChangedHandler?: (event: TaskChangedGroupEvent) => void;
    @Input() columnChangedHandler?: (event: TaskChangedColumnEvent) => void;

    constructor(
        private readonly modelService: BoardModelService,
    ) { }

    slices: SliceDescriptor<T>[] = [];

    rebuildSlices(): void {
        const whole: SliceDescriptor<T> = new SliceDescriptor(null, {
            todo: this.filter
                ? this.modelService.todo.filter(this.filter)
                : this.modelService.todo,
            inprogress: this.filter
                ? this.modelService.inprogress.filter(this.filter)
                : this.modelService.inprogress,
            done: this.filter
                ? this.modelService.done.filter(this.filter)
                : this.modelService.done,
        });
        this.slices = this.grouper ? this.grouper(whole.getAsFlattened()).map(tup =>
            whole.newFromSubset(tup.group).setMetadata(tup.metadata)
        ) : [whole];
    }

    droppedHandler(event: TaskMovedByDnDEvent): void {
        if (event.sourceArray === event.destinationArray) {
            this.handleIntraArrayDrop(event);
        } else {
            this.handleInterArrayDrop(event);
        }
    }

    private handleIntraArrayDrop(event: TaskMovedByDnDEvent) {
        const sliceInfo = this.findSliceInfo(event.destinationArray)!;
        if (!sliceInfo) {
            return;
        }
        const srcPosition = sliceInfo.modelColumn
            .indexOf(event.destinationArray[event.sourceIndex]);
        const dstPosition = sliceInfo.modelColumn
            .indexOf(event.destinationArray[event.destinationIndex]);
        this.columnChangedHandler && this.columnChangedHandler({
            task: event.sourceArray[event.sourceIndex],
            sourceColumn: sliceInfo.modelColumn,
            sourceColumnIndex: srcPosition,
            destinationColumn: sliceInfo.modelColumn,
            destinationColumnIndex: dstPosition,
        });
    }

    private handleInterArrayDrop(event: TaskMovedByDnDEvent) {
        const srcSliceInfo = this.findSliceInfo(event.sourceArray);
        const dstSliceInfo = this.findSliceInfo(event.destinationArray);
        if (!srcSliceInfo || !dstSliceInfo) {
            return;
        }
        const srcPosition = srcSliceInfo.modelColumn
            .indexOf(event.sourceArray[event.sourceIndex]);
        let initialDstPosition = dstSliceInfo.modelColumn
            .indexOf(event.destinationArray[event.destinationIndex]);
        if (initialDstPosition < 0 && event.destinationIndex > 0 && event.destinationArray.length > 0) {
            initialDstPosition = dstSliceInfo.modelColumn
                .indexOf(event.destinationArray[event.destinationIndex - 1]) + 1
        }
        const dstPosition = Math.max(0, initialDstPosition);
        if (this.slices[srcSliceInfo.sliceIndex].metadata != this.slices[dstSliceInfo.sliceIndex].metadata) {
            this.groupChangedHandler && this.groupChangedHandler({
                task: event.sourceArray[event.sourceIndex],
                sourceGroupMetadata: this.slices[srcSliceInfo.sliceIndex].metadata,
                destinationGroupMetadata: this.slices[dstSliceInfo.sliceIndex].metadata,
            });
        }
        this.columnChangedHandler && this.columnChangedHandler({
            task: event.sourceArray[event.sourceIndex],
            sourceColumn: srcSliceInfo.modelColumn,
            sourceColumnIndex: srcPosition,
            destinationColumn: dstSliceInfo.modelColumn,
            destinationColumnIndex: dstPosition,
        });
    }

    private findSliceInfo(slice: Task[]):  SliceInfo | null {
        for (let i = 0; i < this.slices.length; i++) {
            if (this.slices[i].todo == slice)
                return { modelColumn: this.modelService.todo, sliceIndex: i };
            if (this.slices[i].inprogress == slice)
                return { modelColumn: this.modelService.inprogress, sliceIndex: i };
            if (this.slices[i].done == slice)
                return { modelColumn: this.modelService.done, sliceIndex: i };
        }
        return null;
    }

}
