import {Component, Input} from '@angular/core';
import {MatRipple} from "@angular/material/core";
import {NgOptimizedImage, SlicePipe} from "@angular/common";
import {MatTooltip} from "@angular/material/tooltip";
import {User} from "@core/interfaces/boards/user";
import {UserAvatarComponent} from "@pages/utils/user-avatar/user-avatar.component";

@Component({
  selector: 'app-project',
  standalone: true,
  imports: [
    MatRipple,
    SlicePipe,
    NgOptimizedImage,
    MatTooltip,
    UserAvatarComponent
  ],
  templateUrl: './project.component.html',
  styleUrl: './project.component.scss'
})
export class ProjectComponent {

  @Input() title: string = '';
  @Input() members: User[] = [];
}
