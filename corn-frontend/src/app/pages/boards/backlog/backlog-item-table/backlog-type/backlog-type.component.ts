import { Component, Input } from '@angular/core';
import { BacklogItem } from "@interfaces/boards/backlog/backlog.item";
import { BacklogItemType } from "@core/enum/BacklogItemType";
import { MatTooltip } from "@angular/material/tooltip";
import { NgIcon } from "@ng-icons/core";

@Component({
  selector: 'app-backlog-type',
  standalone: true,
  imports: [
    MatTooltip,
    NgIcon
  ],
  templateUrl: './backlog-type.component.html',
  styleUrl: './backlog-type.component.scss'
})
export class BacklogTypeComponent {

  @Input() backlogItem!: BacklogItem;
  protected readonly BacklogItemType = BacklogItemType;
}
