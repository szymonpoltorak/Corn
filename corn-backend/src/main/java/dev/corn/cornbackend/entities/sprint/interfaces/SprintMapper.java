package dev.corn.cornbackend.entities.sprint.interfaces;

import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import org.mapstruct.Mapper;

/**
 * Mapper for Sprint entity
 */
@Mapper(componentModel = "spring")
public interface SprintMapper {
    /**
     * Maps Sprint to SprintResponse
     * @param sprint Sprint to map
     * @return SprintResponse
     */
    SprintResponse toSprintResponse(Sprint sprint);
}
