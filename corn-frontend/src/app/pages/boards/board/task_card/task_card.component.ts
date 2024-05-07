import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { ChangeAssigneeMenuComponent } from '@pages/utils/change_assignee_menu/change_assignee_menu.component';
import { Task } from '@core/interfaces/boards/board/task.interface';
import { TaskChangedGroupEvent } from '@core/interfaces/boards/board/task_changed_group_event.interface';
import { Assignee } from '@core/interfaces/boards/board/assignee.interface';
import { BacklogTypeComponent } from '@pages/boards/backlog/backlog-item-table/backlog-type/backlog-type.component';
import { UserAvatarComponent } from '@pages/utils/user-avatar/user-avatar.component';
import { User } from '@core/interfaces/boards/user';

@Component({
    selector: 'taskcard',
    standalone: true,
    imports: [
        MatCardModule,
        ChangeAssigneeMenuComponent,
        BacklogTypeComponent,
        UserAvatarComponent,
    ],
    templateUrl: './task_card.component.html',
})
export class TaskCardComponent {

    @Input() task?: Task;

    @Input() assigneeChangedHandler?: (event: TaskChangedGroupEvent<Assignee | undefined>) => void;
    @Input() assigneeSupplier?: () => Assignee[];

    protected taskAssigneeAsUser(): User | undefined {
        if (!this.task || !this.task.assignee) {
            return undefined;
        }
        return {
            userId: this.task.assignee.associatedUserId,
            name: this.task.assignee.firstName,
            surname: this.task.assignee.familyName,
            username: this.task.assignee.associatedUsername,
        };
    }

}
