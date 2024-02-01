package dev.corn.cornbackend.api.project.member.interfaces;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberResponse;
import dev.corn.cornbackend.entities.user.User;

import java.util.List;

public interface ProjectMemberService {
    ProjectMemberResponse addMemberToProject(String username, long projectId, User user);

    List<ProjectMemberResponse> getProjectMembers(long projectId, int page, User user);

    ProjectMemberResponse removeMemberFromProject(String username, long projectId, User user);
}
