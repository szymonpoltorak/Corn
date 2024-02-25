package dev.corn.cornbackend.entities.project.member;

import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberMapper;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class ProjectMemberMapperTest {
    @Test
    final void test_toProjectMemberResponse_shouldReturnNull() {
        // given
        ProjectMemberMapper projectMemberMapper = new ProjectMemberMapperImpl();

        // when
        UserResponse actual = projectMemberMapper.mapProjectMememberToUserResponse(null);

        // then
        assertNull(actual, "Should return null");
    }
}
