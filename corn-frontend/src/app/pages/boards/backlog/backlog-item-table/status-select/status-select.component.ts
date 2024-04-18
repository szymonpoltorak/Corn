import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatOption, MatSelect } from "@angular/material/select";
import { NgClass } from "@angular/common";
import { BacklogItem } from "@interfaces/boards/backlog/backlog.item";
import { BacklogItemStatus } from "@core/enum/BacklogItemStatus";

@Component({
    selector: 'app-status-select',
    standalone: true,
    imports: [
        MatSelect,
        NgClass,
        MatSelect,
        MatOption
    ],
    templateUrl: './status-select.component.html',
    styleUrl: './status-select.component.scss'
})
export class StatusSelectComponent {

    @Input() backlogItem!: BacklogItem;

    @Output() statusChange = new EventEmitter<BacklogItem>

    statuses: BacklogItemStatus[] = [
        BacklogItemStatus.TODO,
        BacklogItemStatus.IN_PROGRESS,
        BacklogItemStatus.DONE
    ];

    updateBacklogItem(): void {
        this.statusChange.emit(this.backlogItem);
    }

    getStatusClass(status: BacklogItemStatus): string {
        return status.replace(' ', '_');
    }

}
