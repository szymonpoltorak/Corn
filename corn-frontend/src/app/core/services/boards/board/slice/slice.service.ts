import { Injectable } from '@angular/core';
import { SliceComponent } from '@pages/boards/board/slice/slice.component';

@Injectable()
export class SliceService<T> {

    private slices: Set<SliceComponent<T>> = new Set();

    register(slice: SliceComponent<T>): void {
        this.slices.add(slice);
        this.refresh();
    }

    unregister(slice: SliceComponent<T>): void {
        this.slices.delete(slice);
        this.refresh();
    }

    refresh(): void {
        const droplistInstances = Array.from(this.slices)
            .map(slice => slice.getLocalDroplistInstances())
            .reduce((prev, curr) => [...prev, ...curr], []);
        this.slices.forEach(slice => {
            setTimeout(() => {
                slice.setSiblingDroplistInstances(droplistInstances);
            }, 0);
        });
    }

}