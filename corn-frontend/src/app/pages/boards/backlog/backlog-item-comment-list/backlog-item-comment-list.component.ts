import { AfterViewInit, Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { BacklogItemComment } from "@interfaces/boards/backlog/backlog-item-comment";
import {
    BacklogItemCommentComponent
} from "@pages/boards/backlog/backlog-item-comment-list/backlog-item-comment/backlog-item-comment.component";
import {
    BacklogItemCommentService
} from "@core/services/boards/backlog/backlog-item-comment/backlog-item-comment.service";
import { pipe, Subject, take, takeUntil } from "rxjs";
import { MatPaginator } from "@angular/material/paginator";

@Component({
    selector: 'app-backlog-item-comment-list',
    standalone: true,
    imports: [
        BacklogItemCommentComponent,
        MatPaginator
    ],
    templateUrl: './backlog-item-comment-list.component.html',
    styleUrl: './backlog-item-comment-list.component.scss'
})
export class BacklogItemCommentListComponent implements OnInit, AfterViewInit, OnDestroy {


    @Input() backlogItemId!: number;
    commentsPageNumber: number = 0;
    totalComments: number = 0;
    comments: BacklogItemComment[] = [];

    destroy$: Subject<void> = new Subject();

    @ViewChild(MatPaginator) paginator!: MatPaginator;

    constructor(private commentService: BacklogItemCommentService) {
    }

    ngOnInit(): void {
        this.getAllComments();
    }

    ngAfterViewInit(): void {
        this.paginator.page.pipe(takeUntil(this.destroy$)).subscribe(
            () => {
                this.commentsPageNumber = this.paginator.pageIndex;
                this.commentService.getAllByBacklogItemId(this.backlogItemId, this.commentsPageNumber).pipe(take(1)).subscribe(
                    comments => {
                        this.comments = comments.comments;
                        this.totalComments = comments.totalNumber;
                    }
                )
            }
        )
    }

    getAllComments(): void {
        this.commentService.getAllByBacklogItemId(this.backlogItemId, this.commentsPageNumber).pipe(take(1)).subscribe(
            comments => {
                this.comments = comments.comments;
                this.totalComments = comments.totalNumber;
            }
        )
    }

    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }



}
