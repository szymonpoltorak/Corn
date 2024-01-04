package dev.corn.cornbackend.api.backlog.item;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.api.backlog.item.interfaces.BacklogItemService;
import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.backlog.item.ItemStatus;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemMapper;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemRepository;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberRepository;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintRepository;
import dev.corn.cornbackend.utils.exceptions.backlog.item.BacklogItemNotFoundException;
import dev.corn.cornbackend.utils.exceptions.project.ProjectDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.project.member.ProjectMemberDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.sprint.SprintNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.BACKLOG_ITEM;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.BACKLOG_ITEM_NOT_FOUND_MESSAGE;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.GETTING_BY_ID;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.PROJECT;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.PROJECT_MEMBER;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.PROJECT_MEMBER_NOT_FOUND_MESSAGE;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.PROJECT_NOT_FOUND_MESSAGE;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.RETURNING_BACKLOG_ITEMS_OF_QUANTITY;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.RETURNING_RESPONSE_OF;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.SAVING_AND_RETURNING_RESPONSE_OF;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.SPRINT;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.SPRINT_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class BacklogItemServiceImpl implements BacklogItemService {

    private final BacklogItemRepository backlogItemRepository;
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final BacklogItemMapper backlogItemMapper;

    @Override
    public final BacklogItemResponse getById(long id) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM,id);

        BacklogItem item = backlogItemRepository.findById(id)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info(RETURNING_RESPONSE_OF, item);

        return backlogItemMapper.backlogItemToBacklogItemResponse(item);
    }

    @Override
    public final BacklogItemResponse update(long id, BacklogItemRequest backlogItemRequest) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM, id);

        BacklogItem item = backlogItemRepository.findById(id)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info(GETTING_BY_ID, PROJECT_MEMBER, backlogItemRequest.projectMemberId());

        ProjectMember assignee = projectMemberRepository.findById(backlogItemRequest.projectMemberId())
                .orElseThrow(() -> new ProjectMemberDoesNotExistException(PROJECT_MEMBER_NOT_FOUND_MESSAGE));

        log.info(GETTING_BY_ID, SPRINT, backlogItemRequest.sprintId());

        Sprint sprint = sprintRepository.findById(backlogItemRequest.sprintId())
                .orElseThrow(() -> new SprintNotFoundException(SPRINT_NOT_FOUND_MESSAGE));

        log.info(GETTING_BY_ID, PROJECT, backlogItemRequest.projectId());

        Project project = projectRepository.findById(backlogItemRequest.projectId())
                .orElseThrow(() -> new ProjectDoesNotExistException(PROJECT_NOT_FOUND_MESSAGE));

        BacklogItem newItem = buildUpdatedBacklogItem(id, backlogItemRequest.title(), backlogItemRequest.description(),
                assignee, sprint, project, item.getStatus(), item.getComments());

        log.info(SAVING_AND_RETURNING_RESPONSE_OF, newItem);

        BacklogItem savedItem = backlogItemRepository.save(newItem);

        return backlogItemMapper.backlogItemToBacklogItemResponse(savedItem);
    }

    @Override
    public final BacklogItemResponse deleteById(long id) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM, id);

        BacklogItem item = backlogItemRepository.findById(id)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        backlogItemRepository.deleteById(id);

        log.info(RETURNING_RESPONSE_OF, item);

        return backlogItemMapper.backlogItemToBacklogItemResponse(item);
    }

    @Override
    public final BacklogItemResponse create(BacklogItemRequest backlogItemRequest) {
        log.info(GETTING_BY_ID, PROJECT_MEMBER, backlogItemRequest.projectMemberId());

        ProjectMember assignee = projectMemberRepository.findById(backlogItemRequest.projectMemberId())
                .orElseThrow(() -> new ProjectMemberDoesNotExistException(PROJECT_MEMBER_NOT_FOUND_MESSAGE));

        log.info(GETTING_BY_ID, SPRINT, backlogItemRequest.sprintId());

        Sprint sprint = sprintRepository.findById(backlogItemRequest.sprintId())
                .orElseThrow(() -> new SprintNotFoundException(SPRINT_NOT_FOUND_MESSAGE));

        log.info(GETTING_BY_ID, PROJECT, backlogItemRequest.projectId());

        Project project = projectRepository.findById(backlogItemRequest.projectId())
                .orElseThrow(() -> new ProjectDoesNotExistException(PROJECT_NOT_FOUND_MESSAGE));

        BacklogItem item = buildNewBacklogItem(backlogItemRequest.title(), backlogItemRequest.description(),
                assignee, sprint, project);

        log.info(SAVING_AND_RETURNING_RESPONSE_OF, item);

        BacklogItem savedItem = backlogItemRepository.save(item);

        return backlogItemMapper.backlogItemToBacklogItemResponse(savedItem);
    }

    @Override
    public final List<BacklogItemResponse> getBySprintId(long sprintId) {
        log.info(GETTING_BY_ID, SPRINT, sprintId);

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new SprintNotFoundException(SPRINT_NOT_FOUND_MESSAGE));

        log.info("Getting backlog items for sprint: {}", sprint);

        List<BacklogItem> items = backlogItemRepository.getBySprint(sprint);

        log.info(RETURNING_BACKLOG_ITEMS_OF_QUANTITY, items.size());

        return items.stream()
                .map(backlogItemMapper::backlogItemToBacklogItemResponse)
                .toList();
    }

    @Override
    public final List<BacklogItemResponse> getByProjectId(long projectId) {
        log.info(GETTING_BY_ID, PROJECT, projectId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectDoesNotExistException(PROJECT_NOT_FOUND_MESSAGE));

        log.info("Getting backlog items for project: {}", project);

        List<BacklogItem> items = backlogItemRepository.getByProject(project);

        log.info(RETURNING_BACKLOG_ITEMS_OF_QUANTITY, items.size());

        return items.stream()
                .map(backlogItemMapper::backlogItemToBacklogItemResponse)
                .toList();
    }

    @Override
    public final BacklogItemDetails getDetailsById(long id) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM, id);

        BacklogItem backlogItem = backlogItemRepository.findById(id)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info(RETURNING_RESPONSE_OF, backlogItem);

        return backlogItemMapper.backlogItemToBacklogItemDetails(backlogItem);
    }

    private BacklogItem buildNewBacklogItem(String title, String description, ProjectMember assignee,
                                            Sprint sprint, Project project) {
        return BacklogItem.builder()
                .title(title)
                .description(description)
                .comments(Collections.emptyList())
                .status(ItemStatus.TODO)
                .assignee(assignee)
                .sprint(sprint)
                .project(project)
                .build();
    }

    private BacklogItem buildUpdatedBacklogItem(long id, String title, String description, ProjectMember assignee,
                                                Sprint sprint, Project project, ItemStatus status, List<BacklogItemComment> comments) {
        return BacklogItem.builder()
                .backlogItemId(id)
                .title(title)
                .description(description)
                .comments(comments)
                .status(status)
                .assignee(assignee)
                .sprint(sprint)
                .project(project)
                .build();
    }
}