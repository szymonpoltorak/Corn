import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { ChangeAssigneeMenuComponent } from '../change_assignee_menu/change_assignee_menu.component';
import { Task } from '@core/interfaces/boards/board/task.interface';

@Component({
    selector: 'taskcard',
    standalone: true,
    imports: [
        MatCardModule,
        ChangeAssigneeMenuComponent,
    ],
    templateUrl: './task_card.component.html',
})
export class TaskCardComponent {

    @Input() task?: Task;

}