package dev.corn.cornbackend.entities.sprint;

import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintMapper;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintMapperImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class SprintMapperTest {
    @Test
    final void test_toSprintResponse_shouldReturnNull() {
        // given
        SprintMapper sprintMapper = new SprintMapperImpl();

        // when
        SprintResponse actual = sprintMapper.toSprintResponse(null);

        // then
        assertNull(actual, "Should return null");
    }

    @Test
    final void test_sprintListToSprintResponseList_shouldReturnListOfSprints() {
        // given
        SprintMapper sprintMapper = new SprintMapperImpl();
        Sprint sprint = new Sprint();

        // when
        SprintResponse actual = sprintMapper.toSprintResponse(sprint);

        // then
        assertNotNull(actual, "Should return null");
    }

}
