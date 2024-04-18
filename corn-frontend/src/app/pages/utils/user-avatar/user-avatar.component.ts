import { Component, Input } from '@angular/core';
import { User } from "@core/interfaces/boards/user";
import { MatTooltip } from "@angular/material/tooltip";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { heroUser } from "@ng-icons/heroicons/outline";

@Component({
    selector: 'app-user-avatar',
    standalone: true,
    imports: [
        MatTooltip,
        NgIcon
    ],
    templateUrl: './user-avatar.component.html',
    styleUrl: './user-avatar.component.scss',
    providers: [provideIcons({heroUser})]
})
export class UserAvatarComponent {

    @Input() user: User | undefined  | null;
    @Input() usersLeft: number = 0;
    @Input() isEmpty: boolean = false;

    getInitials(): string {
        if (this.user) {
            return this.user.name.charAt(0) + this.user.surname.charAt(0);
        }
        return '+' + this.usersLeft;
    }
}
