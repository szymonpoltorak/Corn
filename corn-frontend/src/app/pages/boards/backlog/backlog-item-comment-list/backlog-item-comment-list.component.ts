import { Component, Input } from '@angular/core';
import { BacklogItemComment } from "@interfaces/boards/backlog/backlog-item-comment";
import {
  BacklogItemCommentComponent
} from "@pages/boards/backlog/backlog-item-comment-list/backlog-item-comment/backlog-item-comment.component";

@Component({
  selector: 'app-backlog-item-comment-list',
  standalone: true,
  imports: [
    BacklogItemCommentComponent
  ],
  templateUrl: './backlog-item-comment-list.component.html',
  styleUrl: './backlog-item-comment-list.component.scss'
})
export class BacklogItemCommentListComponent {


  @Input() comments!: BacklogItemComment[];
}
