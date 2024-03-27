package dev.corn.cornbackend.api.backlog.comment.data;

import lombok.Builder;

import java.util.List;

@Builder
public record BacklogItemCommentResponseList(List<BacklogItemCommentResponse> comments,
                                             long totalNumber) {
}
