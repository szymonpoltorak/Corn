import { Component, Input } from '@angular/core';
import { BacklogItem } from "@interfaces/boards/backlog/backlog.item";
import { StatusSelectComponent } from "@pages/boards/backlog/backlog-item-table/status-select/status-select.component";
import { BacklogTypeComponent } from "@pages/boards/backlog/backlog-item-table/backlog-type/backlog-type.component";
import { UserAvatarComponent } from "@pages/utils/user-avatar/user-avatar.component";

@Component({
  selector: 'app-backlog-drag',
  standalone: true,
  imports: [
    StatusSelectComponent,
    BacklogTypeComponent,
    UserAvatarComponent
  ],
  templateUrl: './backlog-drag.component.html',
  styleUrl: './backlog-drag.component.scss'
})
export class BacklogDragComponent {

  @Input() backlogItem!: BacklogItem;

}
