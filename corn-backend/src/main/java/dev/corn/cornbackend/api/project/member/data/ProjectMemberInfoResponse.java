package dev.corn.cornbackend.api.project.member.data;

import lombok.Builder;

@Builder
public record ProjectMemberInfoResponse(String fullName, long userId) {
}
