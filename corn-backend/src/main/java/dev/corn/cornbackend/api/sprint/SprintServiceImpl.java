package dev.corn.cornbackend.api.sprint;

import dev.corn.cornbackend.api.sprint.data.SprintRequest;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.api.sprint.interfaces.SprintService;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemRepository;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintMapper;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.utils.exceptions.project.ProjectDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.sprint.InvalidSprintDateException;
import dev.corn.cornbackend.utils.exceptions.sprint.SprintDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.sprint.SprintEndDateMustBeAfterStartDate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static dev.corn.cornbackend.entities.sprint.constants.SprintConstants.SPRINT_START_DATE_FIELD_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {
    public static final int SPRINTS_PER_PAGE = 20;
    private static final int FUTURE_SPRINTS_PER_PAGE = 5;
    private static final String UPDATED_SPRINT = "Updated sprint: {}";
    private static final String FOUND_SPRINT_TO_UPDATE = "Found sprint to update: {}";
    private static final String PROJECT_NOT_FOUND = "Project with projectId: %d does not exist";
    private static final String SPRINTS_ON_PAGE = "Sprints found on page : {}";
    private static final String FOUND_PROJECT_WITH_ID = "Found project with id: {}";
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final BacklogItemRepository backlogItemRepository;
    private final SprintMapper sprintMapper;

    @Override
    public SprintResponse addNewSprint(SprintRequest sprintRequest, User user) {

        if (sprintRequest.endDate().isBefore(sprintRequest.startDate())) {
            throw new SprintEndDateMustBeAfterStartDate(sprintRequest.startDate(), sprintRequest.endDate());
        }
        if (sprintRepository.existsBetweenStartDateAndEndDate(sprintRequest.startDate(),
                sprintRequest.endDate(), sprintRequest.projectId())) {
            throw new InvalidSprintDateException("Sprint with given dates already exists");
        }

        Project project = projectRepository.findByProjectIdAndOwner(sprintRequest.projectId(), user)
                .orElseThrow(() -> new ProjectDoesNotExistException(
                        String.format(PROJECT_NOT_FOUND, sprintRequest.projectId()))
                );
        Sprint sprint = Sprint
                .builder()
                .project(project)
                .sprintName(sprintRequest.name())
                .startDate(sprintRequest.startDate())
                .endDate(sprintRequest.endDate())
                .sprintDescription(sprintRequest.description())
                .build();
        log.info("Instantiated sprint: {}", sprint);

        Sprint newSprint = sprintRepository.save(sprint);

        log.info("Saved sprint: {}", sprint);

        return sprintMapper.toSprintResponse(newSprint);
    }

    @Override
    public SprintResponse getSprintById(long sprintId, User user) {
        log.info("Getting sprint with id: {} for user: {}", sprintId, user);

        Sprint sprint = resolveSprintForProjectMember(sprintId, user);

        log.info("Sprint found : {}", sprint);

        return sprintMapper.toSprintResponse(sprint);
    }

    @Override
    public List<SprintResponse> getSprintsOnPage(int page, long projectId, User user) {
        log.info("Getting sprints in project {} on page: {} for user: {}", projectId, page, user);

        Pageable pageable = PageRequest.of(page, SPRINTS_PER_PAGE);

        Page<Sprint> sprints = sprintRepository.findAllByProjectId(projectId, user, pageable);

        log.info(SPRINTS_ON_PAGE, sprints.getNumberOfElements());

        return sprints.map(sprintMapper::toSprintResponse).toList();
    }

    @Override
    public SprintResponse updateSprintsName(String name, long sprintId, User user) {
        log.info("Updating sprint with id: {} name to: {}", sprintId, name);

        Sprint sprintToUpdate = sprintRepository.findByIdWithProjectOwner(sprintId, user)
                .orElseThrow(() -> new SprintDoesNotExistException(sprintId));

        log.info(FOUND_SPRINT_TO_UPDATE, sprintToUpdate);

        sprintToUpdate.setSprintName(name);

        Sprint updatedSprint = sprintRepository.save(sprintToUpdate);

        log.info(UPDATED_SPRINT, updatedSprint);

        return sprintMapper.toSprintResponse(updatedSprint);
    }

    @Override
    public SprintResponse updateSprintsDescription(String description, long sprintId, User user) {
        log.info("Updating sprint with id: {} description to: {}", sprintId, description);

        Sprint sprintToUpdate = sprintRepository.findByIdWithProjectOwner(sprintId, user)
                .orElseThrow(() -> new SprintDoesNotExistException(sprintId));

        log.info(FOUND_SPRINT_TO_UPDATE, sprintToUpdate);

        sprintToUpdate.setSprintDescription(description);

        Sprint updatedSprint = sprintRepository.save(sprintToUpdate);

        log.info(UPDATED_SPRINT, updatedSprint);

        return sprintMapper.toSprintResponse(updatedSprint);
    }

    @Override
    public SprintResponse updateSprintsStartDate(LocalDate startDate, long sprintId, User user) {
        log.info("Updating sprint with id: {} startDate to: {}", sprintId, startDate);

        if (sprintRepository.existsSprintPeriodWithGivenDate(startDate, sprintId)) {
            throw new InvalidSprintDateException("Start date cannot be after any existing sprint's end date");
        }
        Sprint sprintToUpdate = sprintRepository.findByIdWithProjectOwner(sprintId, user)
                .orElseThrow(() -> new SprintDoesNotExistException(sprintId));

        log.info(FOUND_SPRINT_TO_UPDATE, sprintToUpdate);

        if (sprintToUpdate.isEndBefore(startDate)) {
            throw new SprintEndDateMustBeAfterStartDate(startDate, sprintToUpdate.getEndDate());
        }
        sprintToUpdate.setStartDate(startDate);

        Sprint updatedSprint = sprintRepository.save(sprintToUpdate);

        log.info(UPDATED_SPRINT, updatedSprint);

        return sprintMapper.toSprintResponse(updatedSprint);
    }

    @Override
    public SprintResponse updateSprintsEndDate(LocalDate endDate, long sprintId, User user) {
        log.info("Updating sprint with id: {} endDate to: {}", sprintId, endDate);

        if (sprintRepository.existsSprintPeriodWithGivenDate(endDate, sprintId)) {
            throw new InvalidSprintDateException("End date cannot be before any existing sprint's end date");
        }
        Sprint sprintToUpdate = sprintRepository.findByIdWithProjectOwner(sprintId, user)
                .orElseThrow(() -> new SprintDoesNotExistException(sprintId));

        log.info(FOUND_SPRINT_TO_UPDATE, sprintToUpdate);

        if (sprintToUpdate.isStartAfter(endDate)) {
            throw new SprintEndDateMustBeAfterStartDate(sprintToUpdate.getStartDate(), endDate);
        }
        sprintToUpdate.setEndDate(endDate);

        Sprint updatedSprint = sprintRepository.save(sprintToUpdate);

        log.info(UPDATED_SPRINT, updatedSprint);

        return sprintMapper.toSprintResponse(sprintToUpdate);
    }

    @Override
    @Transactional
    public SprintResponse deleteSprint(long sprintId, User user) {
        log.info("Deleting sprint with id: {}", sprintId);

        Sprint sprintToDelete = sprintRepository.findByIdWithProjectOwner(sprintId, user)
                .orElseThrow(() -> new SprintDoesNotExistException(sprintId));

        log.info("Found sprint to delete: {}", sprintToDelete);

        log.info("Moving all sprints backlog items to backlog...");

        backlogItemRepository.updateSprintItemsToBacklog(sprintToDelete);

        log.info("Deleting sprint...");

        sprintRepository.deleteById(sprintId);

        log.info("Deleted sprint with id: {}", sprintToDelete);

        return sprintMapper.toSprintResponse(sprintToDelete);
    }

    @Override
    public List<SprintResponse> getCurrentAndFutureSprints(long projectId, User user) {
        log.info("Getting current and future sprints for project with id: {}", projectId);

        Project project = resolveProjectForProjectMember(projectId, user);

        log.info(FOUND_PROJECT_WITH_ID, project);

        Pageable pageable = PageRequest.of(0, FUTURE_SPRINTS_PER_PAGE, Sort.by(
                Sort.Direction.ASC, SPRINT_START_DATE_FIELD_NAME)
        );

        Page<Sprint> sprints = sprintRepository.findAllByProjectAndEndDateAfter(project,
                LocalDate.now(), pageable);

        log.info(SPRINTS_ON_PAGE, sprints.getNumberOfElements());

        return sprints.map(sprintMapper::toSprintResponse).toList();
    }

    @Override
    public Page<SprintResponse> getSprintsAfterSprint(long sprintId, Pageable pageable, User user) {
        log.info("Getting sprints after: {}", sprintId);

        Sprint sprint = resolveSprintForProjectMember(sprintId, user);

        log.info("Found sprint: {}", sprint);

        Page<Sprint> sprints = sprintRepository.findAllByProjectAndStartDateAfter(sprint.getProject(),
                sprint.getStartDate(), pageable);

        log.info(SPRINTS_ON_PAGE, sprints.getNumberOfElements());

        return sprints.map(sprintMapper::toSprintResponse);
    }

    @Override
    public Page<SprintResponse> getSprintsBeforeSprint(long sprintId, Pageable pageable, User user) {
        log.info("Getting sprints before: {}", sprintId);

        Sprint sprint = resolveSprintForProjectMember(sprintId, user);

        log.info("Found sprint: {}", sprint);

        Page<Sprint> sprints = sprintRepository.findAllByProjectAndEndDateBefore(sprint.getProject(),
                sprint.getEndDate(), pageable);

        log.info(SPRINTS_ON_PAGE, sprints.getNumberOfElements());

        return sprints.map(sprintMapper::toSprintResponse);
    }

    @Override
    public final List<SprintResponse> getSprintsBetweenDates(LocalDate startDate, LocalDate endDate, long projectId,
                                                             User user) {
        log.info("Getting sprints between dates: {} and {} for project with id: {}", startDate, endDate, projectId);

        if(startDate.isAfter(endDate)) {
            throw new SprintEndDateMustBeAfterStartDate(startDate, endDate);
        }

        Project project = resolveProjectForProjectMember(projectId, user);

        log.info(FOUND_PROJECT_WITH_ID, project);

        List<Sprint> sprints = sprintRepository.findAllBetweenDates(startDate, endDate, project);

        log.info("Found and returning {} sprints", sprints.size());

        return sprints.stream()
                .map(sprintMapper::toSprintResponse)
                .toList();
    }

    private Project resolveProjectForProjectMember(long projectId, User user) {
        return projectRepository.findByIdWithProjectMember(projectId, user)
                .orElseThrow(() -> new ProjectDoesNotExistException(
                        String.format(PROJECT_NOT_FOUND, projectId)
                ));
    }

    private Sprint resolveSprintForProjectMember(long sprintId, User user) {
        return sprintRepository.findByIdWithProjectMember(sprintId, user)
                .orElseThrow(() -> new SprintDoesNotExistException(sprintId));
    }

}
