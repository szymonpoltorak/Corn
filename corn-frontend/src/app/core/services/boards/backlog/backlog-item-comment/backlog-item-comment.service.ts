import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { BacklogItemCommentList } from "@interfaces/boards/backlog/backlog-item-comment-list";
import { Observable } from "rxjs";
import { ApiUrl } from "@core/enum/api-url";
import { BacklogItemComment } from "@interfaces/boards/backlog/backlog-item-comment";

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
      })
    }

    createNewComment(backlogItemId: number, comment: string): Observable<BacklogItemComment> {
      return this.http.post<BacklogItemComment>(ApiUrl.CREATE_COMMENT, {
        backlogItemId: backlogItemId,
        comment: comment
      })
    }

    updateComment(commentId: number, comment: string): Observable<BacklogItemComment> {
        return this.http.put<BacklogItemComment>(ApiUrl.UPDATE_COMMENT, comment, {
            params: {
                commentId: commentId
            }
        })
    }

    deleteComment(commentId: number): Observable<BacklogItemComment> {
        return this.http.delete<BacklogItemComment>(ApiUrl.DELETE_COMMENT, {
            params: {
                commentId: commentId
            }
        })
    }
}
