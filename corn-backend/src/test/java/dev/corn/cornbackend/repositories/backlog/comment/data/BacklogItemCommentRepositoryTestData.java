package dev.corn.cornbackend.repositories.backlog.comment.data;

import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.user.User;
import lombok.Builder;

@Builder
public record BacklogItemCommentRepositoryTestData(
        User owner,
        User commentOwner,
        User nonCommentOwner,
        User nonProjectMember,
        BacklogItemComment comment,
        BacklogItem backlogItemWithComment
) {
}
