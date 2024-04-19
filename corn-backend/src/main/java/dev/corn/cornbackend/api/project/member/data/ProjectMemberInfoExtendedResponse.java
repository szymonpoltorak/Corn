package dev.corn.cornbackend.api.project.member.data;

import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.user.data.UserResponse;

public record ProjectMemberInfoExtendedResponse(UserResponse user, long projectId, long projectMemberId) {

    public static ProjectMemberInfoExtendedResponse fromProjectMember(ProjectMember pm) {
        return new ProjectMemberInfoExtendedResponse(
                new UserResponse(
                        pm.getUser().getUserId(),
                        pm.getUser().getName(),
                        pm.getUser().getSurname(),
                        pm.getUser().getUsername()
                ),
                pm.getProject().getProjectId(),
                pm.getProjectMemberId()
        );
    }

}
