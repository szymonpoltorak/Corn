package dev.corn.cornbackend.test.backlog.item.comment;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentRequest;
import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponseList;
import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import dev.corn.cornbackend.test.backlog.item.comment.data.BacklogItemCommentTestData;
import dev.corn.cornbackend.test.backlog.item.comment.data.UpdateBacklogItemCommentTestData;

import java.util.Collections;
import java.util.List;

public final class BacklogItemCommentTestDataBuilder {

    public static BacklogItemCommentTestData backlogItemCommentTestData() {

        User user = User.builder()
                .userId(1L)
                .name("Name")
                .surname("Surname")
                .authorities(Collections.emptyList())
                .username("Username")
                .build();

        UserResponse userResponse = UserResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .build();

        BacklogItemComment backLogItemComment = BacklogItemComment.builder()
                .backlogItemCommentId(1L)
                .backlogItem(new BacklogItem())
                .user(user)
                .comment("comment")
                .build();

        BacklogItemCommentRequest backlogItemCommentRequest = BacklogItemCommentRequest.builder()
                .backlogItemId(1L)
                .comment("comment")
                .build();

        BacklogItemCommentResponse backlogItemCommentResponse = BacklogItemCommentResponse.builder()
                .comment("comment")
                .user(userResponse)
                .build();

        BacklogItemComment backLogItemCommentToSave = BacklogItemComment.builder()
                .comment(backLogItemComment.getComment())
                .backlogItem(backLogItemComment.getBacklogItem())
                .user(backLogItemComment.getUser())
                .backlogItemCommentId(0L)
                .build();

        BacklogItemCommentResponseList backlogItemCommentResponseList = BacklogItemCommentResponseList.builder()
                .comments(List.of(backlogItemCommentResponse))
                .totalNumber(1L)
                .build();

        return BacklogItemCommentTestData.builder()
                .backlogItemComment(backLogItemComment)
                .backlogItemCommentRequest(backlogItemCommentRequest)
                .backlogItemCommentResponseList(backlogItemCommentResponseList)
                .backlogItemCommentToSave(backLogItemCommentToSave)
                .backlogItemCommentResponse(backlogItemCommentResponse)
                .user(user)
                .build();
    }

    public static UpdateBacklogItemCommentTestData updateBacklogItemCommentTestData() {
        User user = User.builder()
                .userId(1L)
                .name("Name")
                .surname("Surname")
                .authorities(Collections.emptyList())
                .username("Username")
                .build();

        UserResponse userResponse = UserResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .build();
        String newComment = "new comment";

        BacklogItemComment backlogItemComment = BacklogItemComment.builder()
                .backlogItemCommentId(1L)
                .backlogItem(new BacklogItem())
                .user(user)
                .comment("comment")
                .build();

        BacklogItemComment updatedBacklogItemComment = BacklogItemComment.builder()
                .backlogItemCommentId(backlogItemComment.getBacklogItemCommentId())
                .backlogItem(backlogItemComment.getBacklogItem())
                .user(backlogItemComment.getUser())
                .comment(newComment)
                .build();

        BacklogItemCommentResponse backlogItemCommentResponse = BacklogItemCommentResponse.builder()
                .comment(updatedBacklogItemComment.getComment())
                .user(userResponse)
                .build();

        return new UpdateBacklogItemCommentTestData(backlogItemComment, newComment, updatedBacklogItemComment, backlogItemCommentResponse, user);
    }

    private BacklogItemCommentTestDataBuilder() {
    }
}
