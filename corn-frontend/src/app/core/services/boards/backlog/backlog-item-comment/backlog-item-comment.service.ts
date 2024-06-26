import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { BacklogItemCommentList } from "@interfaces/boards/backlog/backlog-item-comment-list";
import { Observable } from "rxjs";
import { ApiUrl } from "@core/enum/api-url";
import { BacklogItemComment } from "@interfaces/boards/backlog/backlog-item-comment";
import { map } from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class BacklogItemCommentService {

    constructor(private http: HttpClient) {
    }

    getAllByBacklogItemId(backlogItemId: number, pageNumber: number): Observable<BacklogItemCommentList> {
        return this.http.get<BacklogItemCommentList>(ApiUrl.GET_COMMENTS_FOR_BACKLOG_ITEM, {
            params: {
                backlogItemId: backlogItemId,
                pageNumber: pageNumber
            }
        }).pipe(
            map((list: BacklogItemCommentList) => {
                list.comments = list.comments.map((comment) => {
                    comment.commentTime = this.convertToLocalDate(comment.commentTime);
                    comment.lastEditTime = this.convertToLocalDate(comment.lastEditTime);
                    return comment;
                })
                return list;
            })
        )
    }

    createNewComment(backlogItemId: number, comment: string): Observable<BacklogItemComment> {
        return this.http.post<BacklogItemComment>(ApiUrl.CREATE_COMMENT, {
            backlogItemId: backlogItemId,
            comment: comment
        }).pipe(
            map((comment: BacklogItemComment) => {
                comment.commentTime = this.convertToLocalDate(comment.commentTime);
                comment.lastEditTime = this.convertToLocalDate(comment.lastEditTime);
                return comment;
            })
        );
    }

    updateComment(commentId: number, comment: string): Observable<BacklogItemComment> {
        return this.http.put<BacklogItemComment>(ApiUrl.UPDATE_COMMENT, comment, {
            params: {
                commentId: commentId
            }
        }).pipe(
            map((comment: BacklogItemComment) => {
                comment.commentTime = this.convertToLocalDate(comment.commentTime);
                comment.lastEditTime = this.convertToLocalDate(comment.lastEditTime);
                return comment;
            })
        );
    }

    deleteComment(commentId: number): Observable<BacklogItemComment> {
        return this.http.delete<BacklogItemComment>(ApiUrl.DELETE_COMMENT, {
            params: {
                commentId: commentId
            }
        });
    }

    convertToLocalDate(date: Date): Date {
        let newDate = new Date(date);
        newDate.setTime(newDate.getTime() - newDate.getTimezoneOffset() * 60 * 1000);
        return newDate;
    }
}
