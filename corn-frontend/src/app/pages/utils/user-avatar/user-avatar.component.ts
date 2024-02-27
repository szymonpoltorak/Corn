import {Component, Input} from '@angular/core';
import {User} from "@core/interfaces/boards/user";
import {MatTooltip} from "@angular/material/tooltip";

@Component({
  selector: 'app-user-avatar',
  standalone: true,
  imports: [
    MatTooltip
  ],
  templateUrl: './user-avatar.component.html',
  styleUrl: './user-avatar.component.scss'
})
export class UserAvatarComponent {

  @Input() user: User | undefined;
  @Input() usersLeft: number = 0;

  getInitials(): string {
    if(this.user !== undefined) {
      return this.user.name.charAt(0) + this.user.surname.charAt(0);
    } else {
      return '+' + this.usersLeft;
    }
  }
}
