package dev.corn.cornbackend.entities.sprint;

import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintMapper;
import org.springframework.stereotype.Component;

@Component
public class SprintMapperImpl implements SprintMapper {

    @Override
    public final SprintResponse toSprintResponse(Sprint sprint) {
        if (sprint == null) {
            return null;
        }
        return SprintResponse
                .builder()
                .sprintId(sprint.getSprintId())
                .projectId(sprint.getProject().getProjectId())
                .sprintName(sprint.getSprintName())
                .sprintDescription(sprint.getSprintDescription())
                .startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .build();
    }
}
