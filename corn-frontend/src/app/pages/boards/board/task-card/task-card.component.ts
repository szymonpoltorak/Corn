import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { ChangeAssigneeMenuComponent } from '@pages/boards/board/change-assignee-menu/change-assignee-menu.component';
import { Task } from '@core/interfaces/boards/board/task.interface';

@Component({
    selector: 'taskcard',
    standalone: true,
    imports: [
        MatCardModule,
        ChangeAssigneeMenuComponent,
    ],
    templateUrl: './task-card.component.html',
})
export class TaskCardComponent {

    @Input() task?: Task;

}
