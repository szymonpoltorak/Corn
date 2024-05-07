import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { MatMenuModule } from '@angular/material/menu';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { BoardModelService } from '@core/services/boards/board/model.service';
import { Assignee } from '@core/interfaces/boards/board/assignee.interface';
import { Task } from '@core/interfaces/boards/board/task.interface';
import { TaskChangedGroupEvent } from '@core/interfaces/boards/board/task_changed_group_event.interface';
import { User } from '@core/interfaces/boards/user';
import { UserAvatarComponent } from '@pages/utils/user-avatar/user-avatar.component';

@Component({
    selector: 'change-assignee-menu',
    standalone: true,
    imports: [
        CommonModule,
        MatMenuModule,
        MatInputModule,
        MatButtonModule,
        MatDividerModule,
        UserAvatarComponent,
    ],
    templateUrl: './change_assignee_menu.component.html',
})
export class ChangeAssigneeMenuComponent {

    protected filteredAssignees: Assignee[] = [];

    @Input() associatedTask?: Task;

    @Input() assigneeChangedHandler?: (event: TaskChangedGroupEvent<Assignee | undefined>) => void;
    @Input() assigneeSupplier?: () => Assignee[];

    @ViewChild('filterStringInput') private input?: ElementRef;

    protected update(): void {
        if (!this.input || ! this.assigneeSupplier)
            return;
        const filterString = this.input.nativeElement.value.toLowerCase();
        this.filteredAssignees = this.assigneeSupplier().filter(a =>
            a.firstName.toLowerCase().includes(filterString) ||
            a.familyName.toLowerCase().includes(filterString) ||
            (a.firstName + " " + a.familyName).toLowerCase().includes(filterString)
        )
    }

    protected assigneeChanged(assignee: Assignee): void {
        if (!this.associatedTask)
            return;
        this.assigneeChangedHandler && this.assigneeChangedHandler({
            task: this.associatedTask,
            sourceGroupMetadata: this.associatedTask.assignee,
            destinationGroupMetadata: assignee
        });
    }

    protected assigneeToUser(assignee: Assignee): User {
        return {
            userId: assignee.associatedUserId,
            name: assignee.firstName,
            surname: assignee.familyName,
            username: assignee.associatedUsername,
        };
    }

}
