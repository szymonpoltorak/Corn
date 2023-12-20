package dev.corn.cornbackend.test.backlog.item.comment;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentRequest;
import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.test.backlog.item.comment.data.BacklogItemCommentTestData;
import dev.corn.cornbackend.test.backlog.item.comment.data.UpdateBacklogItemCommentTestData;

public final class BacklogItemCommentTestDataBuilder {

    public static BacklogItemCommentTestData backlogItemCommentTestData() {

        User user = new User();

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
                .user(user)
                .build();

        BacklogItemComment backLogItemCommentToSave = BacklogItemComment.builder()
                .comment(backLogItemComment.getComment())
                .backlogItem(backLogItemComment.getBacklogItem())
                .user(backLogItemComment.getUser())
                .backlogItemCommentId(0L)
                .build();

        return new BacklogItemCommentTestData(backlogItemCommentRequest, backLogItemComment, backlogItemCommentResponse, backLogItemCommentToSave, user);
    }

    public static UpdateBacklogItemCommentTestData updateBacklogItemCommentTestData() {
        User user = new User();
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
                .user(updatedBacklogItemComment.getUser())
                .build();

        return new UpdateBacklogItemCommentTestData(backlogItemComment, newComment, updatedBacklogItemComment, backlogItemCommentResponse, user);
    }

    private BacklogItemCommentTestDataBuilder() {
    }
}
