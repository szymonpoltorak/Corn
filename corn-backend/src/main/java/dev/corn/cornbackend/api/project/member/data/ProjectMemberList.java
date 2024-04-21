package dev.corn.cornbackend.api.project.member.data;

import dev.corn.cornbackend.entities.user.data.UserResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ProjectMemberList(List<UserResponse> projectMembers, long totalNumber) {
}
