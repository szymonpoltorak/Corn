package dev.corn.cornbackend.entities.sprint.interfaces;

import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.entities.sprint.Sprint;

/**
 * Mapper for Sprint entity
 */
@FunctionalInterface
public interface SprintMapper {
    /**
     * Maps Sprint to SprintResponse
     * @param sprint Sprint to map
     * @return SprintResponse
     */
    SprintResponse toSprintResponse(Sprint sprint);
}
