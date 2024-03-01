import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { Task } from '../model/model';
import { ChangeAssigneeMenuComponent } from '../change_assignee_menu/change_assignee_menu.component';

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
