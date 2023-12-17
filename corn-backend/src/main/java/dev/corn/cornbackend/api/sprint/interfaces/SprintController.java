package dev.corn.cornbackend.api.sprint.interfaces;

import dev.corn.cornbackend.api.sprint.data.SprintRequest;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.user.User;

import java.time.LocalDate;
import java.util.List;

public interface SprintController {

    SprintResponse addNewSprint(SprintRequest sprintRequest, User user);

    SprintResponse getSprintById(long sprintId, User user);

    List<SprintResponse> getSprintsOnPage(int page, long projectId, User user);

    SprintResponse updateSprintsName(String name, long sprintId, User user);

    SprintResponse updateSprintsDescription(String description, long sprintId, User user);

    SprintResponse updateSprintsStartDate(LocalDate startDate, long sprintId, User user);

    SprintResponse updateSprintsEndDate(LocalDate endDate, long sprintId, User user);

    SprintResponse deleteSprint(long sprintId, User user);

}
