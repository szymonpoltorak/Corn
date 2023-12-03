package dev.corn.cornbackend.entities.project.member;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberResponse;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class ProjectMemberMapperTest {
    @Test
    final void test_toProjectMemberResponse_shouldReturnNull() {
        // given
        ProjectMemberMapper projectMemberMapper = new ProjectMemberMapperImpl();

        // when
        ProjectMemberResponse actual = projectMemberMapper.toProjectMemberResponse(null);

        // then
        assertNull(actual, "Should return null");
    }
}
