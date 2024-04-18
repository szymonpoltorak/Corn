package dev.corn.cornbackend.entities.project.member;

import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberMapper;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class ProjectMemberMapperImpl implements ProjectMemberMapper {
    @Override
    public final UserResponse mapProjectMememberToUserResponse(ProjectMember projectMember) {
        if (projectMember == null) {
            return null;
        }
        User user = projectMember.getUser();

        return UserResponse
                .builder()
                .userId(projectMember.getProjectMemberId())
                .name(user.getName())
                .username(user.getUsername())
                .surname(user.getSurname())
                .build();
    }
}
