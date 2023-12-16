package dev.corn.cornbackend.api.sprint.interfaces;

import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.entities.user.User;

import java.time.LocalDate;
import java.util.List;

public interface SprintController {

    SprintResponse addNewSprint(String name, LocalDate startDate, LocalDate endDate, String description);

    SprintResponse getSprintById(long sprintId, User user);

    List<SprintResponse> getSprintsOnPage(int page, User user);

    SprintResponse updateSprintsName(String name, long sprintId);

    SprintResponse updateSprintsDescription(String description, long sprintId);

    SprintResponse updateSprintsStartDate(LocalDate startDate, long sprintId);

    SprintResponse updateSprintsEndDate(LocalDate endDate, long sprintId);

    SprintResponse deleteSprint(long sprintId);

}
