import { AfterViewInit, Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { BacklogItemComment } from "@interfaces/boards/backlog/backlog-item-comment";
import {
    BacklogItemCommentComponent
} from "@pages/boards/backlog/backlog-item-comment-list/backlog-item-comment/backlog-item-comment.component";
import {
    BacklogItemCommentService
} from "@core/services/boards/backlog/backlog-item-comment/backlog-item-comment.service";
import { Subject, take, takeUntil } from "rxjs";
import { MatPaginator } from "@angular/material/paginator";
import { MatFormField, MatInput } from "@angular/material/input";
import { FormControl, ReactiveFormsModule, Validators } from "@angular/forms";
import { CustomValidators } from "@core/validators/custom-validators";
import { MatButton } from "@angular/material/button";
import { MatHint } from "@angular/material/form-field";
import { NgIf } from "@angular/common";

@Component({
    selector: 'app-backlog-item-comment-list',
    standalone: true,
    imports: [
        BacklogItemCommentComponent,
        MatPaginator,
        MatInput,
        MatFormField,
        ReactiveFormsModule,
        MatButton,
        MatHint,
        NgIf,
    ],
    templateUrl: './backlog-item-comment-list.component.html',
    styleUrl: './backlog-item-comment-list.component.scss'
})
export class BacklogItemCommentListComponent implements OnInit, AfterViewInit, OnDestroy {


    @Input() backlogItemId!: number;

    @Output() changedPage: EventEmitter<void> = new EventEmitter<void>;

    commentsPageNumber: number = 0;
    totalComments: number = 0;
    comments: BacklogItemComment[] = [];

    destroy$: Subject<void> = new Subject();

    @ViewChild(MatPaginator) paginator!: MatPaginator;

    newComment: FormControl = new FormControl('', [Validators.required, CustomValidators.notWhitespace()])


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
                        this.changedPage.emit();
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

    resetNewComment(): void {
        this.newComment.reset();
    }

    addNewComment(): void {
        if(this.newComment.invalid) {
            return;
        }

        this.commentService.createNewComment(this.backlogItemId, this.newComment.value).pipe(take(1)).subscribe(
            () => {
                this.resetNewComment();
                this.getAllComments();
            }
        )
    }

    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}
