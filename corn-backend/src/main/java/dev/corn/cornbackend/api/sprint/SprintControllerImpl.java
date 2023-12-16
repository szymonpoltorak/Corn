package dev.corn.cornbackend.api.sprint;

import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.api.sprint.interfaces.SprintController;
import dev.corn.cornbackend.api.sprint.interfaces.SprintService;
import dev.corn.cornbackend.config.jwtprocessing.JwtAuthed;
import dev.corn.cornbackend.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.ADD_SPRINT;
import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.DELETE_SPRINT;
import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.GET_SPRINTS_ON_PAGE;
import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.GET_SPRINT_BY_ID;
import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.SPRINT_API_ENDPOINT;
import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.UPDATE_SPRINTS_DESCRIPTION;
import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.UPDATE_SPRINTS_END_DATE;
import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.UPDATE_SPRINTS_NAME;
import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.UPDATE_SPRINTS_START_DATE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = SPRINT_API_ENDPOINT)
public class SprintControllerImpl implements SprintController {

    private final SprintService sprintService;

    @Override
    @PostMapping(value = ADD_SPRINT)
    public SprintResponse addNewSprint(@RequestParam String name, @RequestParam LocalDate startDate,
                                       @RequestParam LocalDate endDate, @RequestParam String description) {
        return sprintService.addNewSprint(name, startDate, endDate, description);
    }

    @Override
    @GetMapping(value = GET_SPRINT_BY_ID)
    public SprintResponse getSprintById(@RequestParam long sprintId, @JwtAuthed User user) {
        return sprintService.getSprintById(sprintId, user);
    }

    @Override
    @GetMapping(value = GET_SPRINTS_ON_PAGE)
    public List<SprintResponse> getSprintsOnPage(@RequestParam int page, @JwtAuthed User user) {
        return sprintService.getSprintsOnPage(page, user);
    }

    @Override
    @PatchMapping(value = UPDATE_SPRINTS_NAME)
    public SprintResponse updateSprintsName(@RequestParam String name, @RequestParam long sprintId) {
        return sprintService.updateSprintsName(name, sprintId);
    }

    @Override
    @PatchMapping(value = UPDATE_SPRINTS_DESCRIPTION)
    public SprintResponse updateSprintsDescription(@RequestParam String description, @RequestParam long sprintId) {
        return sprintService.updateSprintsDescription(description, sprintId);
    }

    @Override
    @PatchMapping(value = UPDATE_SPRINTS_START_DATE)
    public SprintResponse updateSprintsStartDate(@RequestParam LocalDate startDate, @RequestParam long sprintId) {
        return sprintService.updateSprintsStartDate(startDate, sprintId);
    }

    @Override
    @PatchMapping(value = UPDATE_SPRINTS_END_DATE)
    public SprintResponse updateSprintsEndDate(@RequestParam LocalDate endDate, @RequestParam long sprintId) {
        return sprintService.updateSprintsEndDate(endDate, sprintId);
    }

    @Override
    @DeleteMapping(value = DELETE_SPRINT)
    public SprintResponse deleteSprint(@RequestParam long sprintId) {
        return sprintService.deleteSprint(sprintId);
    }

}
