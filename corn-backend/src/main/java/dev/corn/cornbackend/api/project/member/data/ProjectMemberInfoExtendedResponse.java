package dev.corn.cornbackend.api.project.member.data;

import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.data.UserResponse;

public record ProjectMemberInfoExtendedResponse(UserResponse user, long projectId, long projectMemberId) {

    public static ProjectMemberInfoExtendedResponse fromProjectMember(ProjectMember pm) {
        User user = pm.getUser();
        return new ProjectMemberInfoExtendedResponse(
                new UserResponse(
                        user.getUserId(),
                        user.getName(),
                        user.getSurname(),
                        user.getUsername()
                ),
                pm.getProject().getProjectId(),
                pm.getProjectMemberId()
        );
    }

}
