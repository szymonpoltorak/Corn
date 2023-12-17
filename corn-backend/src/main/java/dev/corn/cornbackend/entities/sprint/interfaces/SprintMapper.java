package dev.corn.cornbackend.entities.sprint.interfaces;

import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SprintMapper {
    SprintResponse toSprintResponse(Sprint sprint);
}
