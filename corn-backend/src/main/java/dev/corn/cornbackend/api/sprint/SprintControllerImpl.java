package dev.corn.cornbackend.api.sprint;

import dev.corn.cornbackend.api.sprint.data.SprintRequest;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.api.sprint.interfaces.SprintController;
import dev.corn.cornbackend.api.sprint.interfaces.SprintService;
import dev.corn.cornbackend.config.jwtprocessing.JwtAuthed;
import dev.corn.cornbackend.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.ADD_SPRINT;
import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.CURRENT_AND_FUTURE_SPRINTS;
import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.DELETE_SPRINT;
import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.GET_SPRINTS_ON_PAGE;
import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.GET_SPRINT_BY_ID;
import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.SPRINTS_AFTER_SPRINT;
import static dev.corn.cornbackend.api.sprint.constants.SprintMappings.SPRINTS_BEFORE_SPRINT;
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
    public final SprintResponse addNewSprint(@RequestBody SprintRequest sprintRequest, @JwtAuthed User user) {
        return sprintService.addNewSprint(sprintRequest, user);
    }

    @Override
    @GetMapping(value = GET_SPRINT_BY_ID)
    public final SprintResponse getSprintById(@RequestParam long sprintId, @JwtAuthed User user) {
        return sprintService.getSprintById(sprintId, user);
    }

    @Override
    @GetMapping(value = GET_SPRINTS_ON_PAGE)
    public final List<SprintResponse> getSprintsOnPage(@RequestParam int page, @RequestParam long projectId,
                                                       @JwtAuthed User user) {
        return sprintService.getSprintsOnPage(page, projectId, user);
    }

    @Override
    @PutMapping(value = UPDATE_SPRINTS_NAME)
    public final SprintResponse updateSprintsName(@RequestParam String name, @RequestParam long sprintId,
                                                  @JwtAuthed User user) {
        return sprintService.updateSprintsName(name, sprintId, user);
    }

    @Override
    @PutMapping(value = UPDATE_SPRINTS_DESCRIPTION)
    public final SprintResponse updateSprintsDescription(@RequestParam String description,
                                                         @RequestParam long sprintId,
                                                         @JwtAuthed User user) {
        return sprintService.updateSprintsDescription(description, sprintId, user);
    }

    @Override
    @PutMapping(value = UPDATE_SPRINTS_START_DATE)
    public final SprintResponse updateSprintsStartDate(@RequestParam LocalDate startDate,
                                                       @RequestParam long sprintId,
                                                       @JwtAuthed User user) {
        return sprintService.updateSprintsStartDate(startDate, sprintId, user);
    }

    @Override
    @PutMapping(value = UPDATE_SPRINTS_END_DATE)
    public final SprintResponse updateSprintsEndDate(@RequestParam LocalDate endDate, @RequestParam long sprintId,
                                                     @JwtAuthed User user) {
        return sprintService.updateSprintsEndDate(endDate, sprintId, user);
    }

    @Override
    @DeleteMapping(value = DELETE_SPRINT)
    public final SprintResponse deleteSprint(@RequestParam long sprintId, @JwtAuthed User user) {
        return sprintService.deleteSprint(sprintId, user);
    }

    @Override
    @GetMapping(value = CURRENT_AND_FUTURE_SPRINTS)
    public final List<SprintResponse> getCurrentAndFutureSprints(@RequestParam long projectId,
                                                                 @JwtAuthed User user) {
        return sprintService.getCurrentAndFutureSprints(projectId, user);
    }

    @Override
    @GetMapping(value = SPRINTS_AFTER_SPRINT)
    public final Page<SprintResponse> getSprintsAfterSprint(@RequestParam long sprintId,
                                                            Pageable pageable,
                                                            @JwtAuthed User user) {
        return sprintService.getSprintsAfterSprint(sprintId, pageable, user);
    }

    @Override
    @GetMapping(value = SPRINTS_BEFORE_SPRINT)
    public final Page<SprintResponse> getSprintsBeforeSprint(@RequestParam long sprintId,
                                                             Pageable pageable,
                                                             @JwtAuthed User user) {
        return sprintService.getSprintsBeforeSprint(sprintId, pageable, user);
    }

}
