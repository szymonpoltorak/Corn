import { Injectable } from '@angular/core';
import { SliceComponent } from './slice.component';

@Injectable()
export class SliceService {

    private slices: Set<SliceComponent> = new Set();

    register(slice: SliceComponent) {
        this.slices.add(slice);
        this.refresh();
    }

    unregister(slice: SliceComponent) {
        this.slices.delete(slice);
        this.refresh();
    }

    refresh() {
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