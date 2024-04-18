import { Component, Input } from '@angular/core';
import { BacklogItemType } from "@core/enum/BacklogItemType";
import { MatTooltip } from "@angular/material/tooltip";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { bootstrapBugFill } from "@ng-icons/bootstrap-icons";
import { featherBook } from "@ng-icons/feather-icons";
import { matTask } from "@ng-icons/material-icons/baseline";
import { octContainer } from "@ng-icons/octicons";

@Component({
  selector: 'app-backlog-type',
  standalone: true,
  imports: [
    MatTooltip,
    NgIcon
  ],
  templateUrl: './backlog-type.component.html',
  styleUrl: './backlog-type.component.scss'
  ,providers: [provideIcons({ bootstrapBugFill, featherBook, matTask, octContainer })]
})
export class BacklogTypeComponent {

  @Input() backlogItemType!: BacklogItemType;
  protected readonly BacklogItemType = BacklogItemType;
}
