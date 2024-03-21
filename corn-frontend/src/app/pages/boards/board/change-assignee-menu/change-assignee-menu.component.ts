import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { MatMenuModule } from '@angular/material/menu';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { BoardModelService } from '@core/services/boards/board/model.service';
import { Assignee } from '@core/interfaces/boards/board/assignee.interface';
import { Task } from '@core/interfaces/boards/board/task.interface';

@Component({
    selector: 'change-assignee-menu',
    standalone: true,
    imports: [
        CommonModule,
        MatMenuModule,
        MatInputModule,
        MatButtonModule,
        MatDividerModule,
    ],
    templateUrl: './change-assignee-menu.component.html',
})
export class ChangeAssigneeMenuComponent {

    protected filteredAssignees: Assignee[] = [];

    @Input() associatedTask?: Task;

    @ViewChild('filterStringInput') private input?: ElementRef;

    constructor(
        private readonly modelService: BoardModelService,
    ) { }

    protected update(): void {
        if (!this.input)
            return;
        const filterString = this.input.nativeElement.value.toLowerCase();
        this.filteredAssignees = this.modelService.assignees.filter(a =>
            a.firstName.toLowerCase().includes(filterString) ||
            a.familyName.toLowerCase().includes(filterString) ||
            (a.firstName + " " + a.familyName).toLowerCase().includes(filterString)
        )
    }

    protected assigneeChanged(assignee: Assignee): void {
        if (!this.associatedTask)
            return;
        this.modelService.setAssigneeForTask(this.associatedTask, assignee);
    }

}
