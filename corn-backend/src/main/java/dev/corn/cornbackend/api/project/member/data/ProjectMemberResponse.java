package dev.corn.cornbackend.api.project.member.data;

import lombok.Builder;

@Builder
public record ProjectMemberResponse(long projectMemberId, String username, String fullName) {
}
