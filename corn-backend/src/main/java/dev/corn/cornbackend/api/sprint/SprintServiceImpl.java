package dev.corn.cornbackend.api.sprint;

import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.api.sprint.interfaces.SprintService;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintMapper;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.utils.exceptions.sprint.SprintDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.sprint.SprintEndDateMustBeAfterStartDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {
    public static final int SPRINTS_PER_PAGE = 20;
    private final SprintRepository sprintRepository;
    private final SprintMapper sprintMapper;

    @Override
    public final SprintResponse addNewSprint(String name, LocalDate startDate, LocalDate endDate, String description) {
        Sprint sprint = Sprint
                .builder()
                .sprintName(name)
                .sprintStartDate(startDate)
                .sprintEndDate(endDate)
                .sprintDescription(description)
                .build();
        if (endDate.isBefore(startDate)) {
            throw new SprintEndDateMustBeAfterStartDate(startDate, endDate);
        }
        log.info("Instantiated sprint: {}", sprint);

        Sprint newSprint = sprintRepository.save(sprint);
        log.info("Saved sprint: {}", sprint);

        return sprintMapper.toSprintResponse(newSprint);
    }

    @Override
    public final SprintResponse getSprintById(long sprintId, User user) {
        log.info("Getting sprint with id: {} for user: {}", sprintId, user);

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new SprintDoesNotExistException(sprintId));
        log.info("Sprint found : {}", sprint);

        return sprintMapper.toSprintResponse(sprint);
    }

    @Override
    public final List<SprintResponse> getSprintsOnPage(int page, User user) {
        log.info("Getting sprints on page: {} for user: {}", page, user);

        Pageable pageable = PageRequest.of(page, SPRINTS_PER_PAGE);
        Page<Sprint> sprints = sprintRepository.findAllByOwnerOrderByName(user, pageable);
        log.info("Sprints found on page : {}", sprints.getTotalElements());

        return sprints.map(sprintMapper::toSprintResponse).toList();
    }

    @Override
    public final SprintResponse updateSprintsName(String name, long sprintId) {
        log.info("Updating sprint with id: {} name to: {}", sprintId, name);

        Sprint sprintToUpdate = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new SprintDoesNotExistException(sprintId));
        log.info("Found sprint to update: {}", sprintToUpdate);

        sprintToUpdate.setSprintName(name);
        Sprint updatedSprint = sprintRepository.save(sprintToUpdate);
        log.info("Updated sprint: {}", updatedSprint);

        return sprintMapper.toSprintResponse(updatedSprint);
    }

    @Override
    public final SprintResponse updateSprintsDescription(String description, long sprintId) {
        log.info("Updating sprint with id: {} description to: {}", sprintId, description);

        Sprint sprintToUpdate = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new SprintDoesNotExistException(sprintId));
        log.info("Found sprint to update: {}", sprintToUpdate);

        sprintToUpdate.setSprintDescription(description);
        Sprint updatedSprint = sprintRepository.save(sprintToUpdate);
        log.info("Updated sprint: {}", updatedSprint);

        return sprintMapper.toSprintResponse(updatedSprint);
    }

    @Override
    public final SprintResponse updateSprintsStartDate(LocalDate startDate, long sprintId) {
        log.info("Updating sprint with id: {} startDate to: {}", sprintId, startDate);

        Sprint sprintToUpdate = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new SprintDoesNotExistException(sprintId));
        log.info("Found sprint to update: {}", sprintToUpdate);

        if (sprintToUpdate.isEndBefore(startDate)) {
            throw new SprintEndDateMustBeAfterStartDate(startDate, sprintToUpdate.getSprintEndDate());
        }

        sprintToUpdate.setSprintStartDate(startDate);
        Sprint updatedSprint = sprintRepository.save(sprintToUpdate);
        log.info("Updated sprint: {}", updatedSprint);

        return sprintMapper.toSprintResponse(updatedSprint);
    }

    @Override
    public final SprintResponse updateSprintsEndDate(LocalDate endDate, long sprintId) {
        log.info("Updating sprint with id: {} endDate to: {}", sprintId, endDate);

        Sprint sprintToUpdate = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new SprintDoesNotExistException(sprintId));
        log.info("Found sprint to update: {}", sprintToUpdate);

        if (sprintToUpdate.isStartAfter(endDate)) {
            throw new SprintEndDateMustBeAfterStartDate(sprintToUpdate.getSprintStartDate(), endDate);
        }

        sprintToUpdate.setSprintEndDate(endDate);
        Sprint updatedSprint = sprintRepository.save(sprintToUpdate);
        log.info("Updated sprint: {}", updatedSprint);

        return sprintMapper.toSprintResponse(sprintToUpdate);
    }

    @Override
    public final SprintResponse deleteSprint(long sprintId) {
        log.info("Deleting sprint with id: {}", sprintId);

        Sprint sprintToDelete = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new SprintDoesNotExistException(sprintId));
        log.info("Found sprint to delete: {}", sprintToDelete);

        sprintRepository.deleteById(sprintId);
        log.info("Deleted sprint with id: {}", sprintToDelete);

        return sprintMapper.toSprintResponse(sprintToDelete);
    }

}
