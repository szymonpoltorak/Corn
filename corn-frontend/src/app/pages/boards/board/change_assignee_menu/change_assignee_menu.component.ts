import { ElementRef, Input } from '@angular/core';
import { Assignee, Task } from '../model/model';
import { MatMenuModule } from '@angular/material/menu';
import { Component, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { ModelService } from '../model/model.service';

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
    templateUrl: './change_assignee_menu.component.html',
})
export class ChangeAssigneeMenuComponent {

    protected myControl = new FormControl('');
    protected filteredAssignees: Assignee[] = [];

    @Input() associatedTask?: Task;

    @ViewChild('filterStringInput') private input?: ElementRef;

    constructor(
        protected readonly modelService: ModelService,
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
