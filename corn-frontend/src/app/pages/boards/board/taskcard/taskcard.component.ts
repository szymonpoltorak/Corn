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
    templateUrl: './taskcard.component.html',
})
export class TaskcardComponent {

    @Input() task?: Task;

}
