package dev.corn.cornbackend.api.project.member.interfaces;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberResponse;

import java.util.List;

public interface ProjectMemberController {
    ProjectMemberResponse addMemberToProject(String username, long projectId);

    List<ProjectMemberResponse> getProjectMembers(long projectId, int page);

    ProjectMemberResponse removeMemberFromProject(String username, long projectId);
}
