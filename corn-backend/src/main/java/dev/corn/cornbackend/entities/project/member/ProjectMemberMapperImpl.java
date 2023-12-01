package dev.corn.cornbackend.entities.project.member;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberResponse;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberMapper;
import dev.corn.cornbackend.entities.user.User;
import org.springframework.stereotype.Component;

@Component
public class ProjectMemberMapperImpl implements ProjectMemberMapper {
    @Override
    public final ProjectMemberResponse toProjectMemberResponse(ProjectMember projectMember) {
        if (projectMember == null) {
            return null;
        }
        User user = projectMember.getUser();

        return ProjectMemberResponse
                .builder()
                .fullName(user.getFullName())
                .projectMemberId(projectMember.getProjectMemberId())
                .username(user.getUsername())
                .build();
    }
}
