package dev.corn.cornbackend.api.project.member;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberResponse;
import dev.corn.cornbackend.api.project.member.interfaces.ProjectMemberController;
import dev.corn.cornbackend.api.project.member.interfaces.ProjectMemberService;
import dev.corn.cornbackend.config.jwtprocessing.JwtAuthed;
import dev.corn.cornbackend.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static dev.corn.cornbackend.api.project.member.constants.ProjectMemberMappings.ADD_MEMBER_TO_PROJECT_MAPPING;
import static dev.corn.cornbackend.api.project.member.constants.ProjectMemberMappings.GET_MEMBERS_OF_PROJECT_MAPPING;
import static dev.corn.cornbackend.api.project.member.constants.ProjectMemberMappings.PROJECT_MEMBER_MAPPING;
import static dev.corn.cornbackend.api.project.member.constants.ProjectMemberMappings.REMOVE_MEMBER_FROM_PROJECT_MAPPING;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = PROJECT_MEMBER_MAPPING)
public class ProjectMemberControllerImpl implements ProjectMemberController {
    private final ProjectMemberService projectMemberService;

    @Override
    @PostMapping(value = ADD_MEMBER_TO_PROJECT_MAPPING)
    public final ProjectMemberResponse addMemberToProject(@RequestParam String username,
                                                          @RequestParam long projectId,
                                                          @JwtAuthed User user) {
        return projectMemberService.addMemberToProject(username, projectId, user);
    }

    @Override
    @GetMapping(value = GET_MEMBERS_OF_PROJECT_MAPPING)
    public final List<ProjectMemberResponse> getProjectMembers(@RequestParam long projectId,
                                                               @RequestParam int page,
                                                               @JwtAuthed User user) {
        return projectMemberService.getProjectMembers(projectId, page, user);
    }

    @Override
    @DeleteMapping(value = REMOVE_MEMBER_FROM_PROJECT_MAPPING)
    public final ProjectMemberResponse removeMemberFromProject(@RequestParam String username,
                                                               @RequestParam long projectId,
                                                               @JwtAuthed User user) {
        return projectMemberService.removeMemberFromProject(username, projectId, user);
    }
}
