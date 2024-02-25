import { Injectable, Input } from '@angular/core';
import { SliceDescriptor } from './slice_descriptor';
import { Task } from '../model/model';
import { ModelService } from '../model/model.service';

@Injectable()
export class SlicesModelService {

    @Input() filter?: (task: Task) => boolean;
    @Input() grouper?: (ungrouped: Task[]) => { group: Task[], metadata: any }[];

    @Input() groupChangedHandler?: (event: TaskChangedGroupEvent) => void;
    @Input() columnChangedHandler?: (event: TaskChangedColumnEvent) => void;

    constructor(
        private readonly modelService: ModelService,
    ) { }

    slices: SliceDescriptor[] = [];

    rebuildSlices() {
        const whole: SliceDescriptor = new SliceDescriptor(null, {
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

    droppedHandler(event: TaskMovedByDnDEvent) {
        if (event.sourceArray === event.destinationArray) {
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
        } else {
            const srcSliceInfo = this.findSliceInfo(event.sourceArray);
            const dstSliceInfo = this.findSliceInfo(event.destinationArray);
            if (!srcSliceInfo || !dstSliceInfo) {
                return;
            }
            const srcPosition = srcSliceInfo.modelColumn
                .indexOf(event.sourceArray[event.sourceIndex]);
            // there still exists at least one edge-case when dstPosition
            // calculation breaks, but the resulting bug is pretty insignificant
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
    }

    private findSliceInfo(slice: Task[]): { modelColumn: Task[], sliceIndex: number } | null {
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

export interface TaskMovedByDnDEvent {
    sourceArray: Task[],
    sourceIndex: number,
    destinationArray: Task[],
    destinationIndex: number,
}

export interface TaskChangedGroupEvent {
    task: Task,
    sourceGroupMetadata: any,
    destinationGroupMetadata: any,
}

export interface TaskChangedColumnEvent {
    task: Task,
    sourceColumn: Task[],
    sourceColumnIndex: number,
    destinationColumn: Task[],
    destinationColumnIndex: number,
}