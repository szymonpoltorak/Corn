package dev.corn.cornbackend.api.backlog.item;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.api.backlog.item.interfaces.BacklogItemService;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class BacklogItemServiceImpl implements BacklogItemService {

    private final BacklogItemRepository backlogItemRepository;
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final BacklogItemMapper backlogItemMapper;

    private static final String GETTING_BY_ID = "Getting {} by id: {}";
    private static final String BACKLOG_ITEM_NOT_FOUND_MESSAGE = "Backlog item not found";
    private static final String RETURNING_RESPONSE_OF = "Returning response of: {}";
    private static final String PROJECT_MEMBER_NOT_FOUND_MESSAGE = "Project member not found";
    private static final String SPRINT_NOT_FOUND_MESSAGE = "Sprint not found";
    private static final String PROJECT_NOT_FOUND_MESSAGE = "Project not found";
    private static final String BACKLOG_ITEM = "BacklogItem";
    private static final String PROJECT_MEMBER = "ProjectMember";
    private static final String SPRINT = "Sprint";
    private static final String PROJECT = "Project";
    private static final String RETURNING_BACKLOG_ITEMS_OF_QUANTITY = "Returning backlog items of quantity: {}";
    private static final String SAVING_AND_RETURNING_RESPONSE_OF = "Saving and returning response of: {}";

    @Override
    public BacklogItemResponse getById(long id) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM,id);

        BacklogItem item = backlogItemRepository.findById(id)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info(RETURNING_RESPONSE_OF, item);

        return backlogItemMapper.backlogItemToBacklogItemResponse(item);
    }

    @Override
    public BacklogItemResponse update(long id, BacklogItemRequest backlogItemRequest) {
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

        BacklogItem newItem = BacklogItem
                .builder()
                .title(backlogItemRequest.title())
                .description(backlogItemRequest.description())
                .comments(item.getComments())
                .status(ItemStatus.TODO)
                .assignee(assignee)
                .sprint(sprint)
                .backlogItemId(id)
                .project(project)
                .build();

        log.info(SAVING_AND_RETURNING_RESPONSE_OF, newItem);

        BacklogItem savedItem = backlogItemRepository.save(newItem);

        return backlogItemMapper.backlogItemToBacklogItemResponse(savedItem);
    }

    @Override
    public BacklogItemResponse deleteById(long id) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM, id);

        BacklogItem item = backlogItemRepository.findById(id)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        backlogItemRepository.deleteById(id);

        log.info(RETURNING_RESPONSE_OF, item);

        return backlogItemMapper.backlogItemToBacklogItemResponse(item);
    }

    @Override
    public BacklogItemResponse create(BacklogItemRequest backlogItemRequest) {
        log.info(GETTING_BY_ID, PROJECT_MEMBER, backlogItemRequest.projectMemberId());

        ProjectMember assignee = projectMemberRepository.findById(backlogItemRequest.projectMemberId())
                .orElseThrow(() -> new ProjectMemberDoesNotExistException(PROJECT_MEMBER_NOT_FOUND_MESSAGE));

        log.info(GETTING_BY_ID, SPRINT, backlogItemRequest.sprintId());

        Sprint sprint = sprintRepository.findById(backlogItemRequest.sprintId())
                .orElseThrow(() -> new SprintNotFoundException(SPRINT_NOT_FOUND_MESSAGE));

        log.info(GETTING_BY_ID, PROJECT, backlogItemRequest.projectId());

        Project project = projectRepository.findById(backlogItemRequest.projectId())
                .orElseThrow(() -> new ProjectDoesNotExistException(PROJECT_NOT_FOUND_MESSAGE));

        BacklogItem item = BacklogItem
                .builder()
                .title(backlogItemRequest.title())
                .description(backlogItemRequest.description())
                .comments(Collections.emptyList())
                .status(ItemStatus.TODO)
                .assignee(assignee)
                .sprint(sprint)
                .project(project)
                .build();

        log.info(SAVING_AND_RETURNING_RESPONSE_OF, item);

        BacklogItem savedItem = backlogItemRepository.save(item);

        return backlogItemMapper.backlogItemToBacklogItemResponse(savedItem);
    }

    @Override
    public List<BacklogItemResponse> getBySprintId(long sprintId) {
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
    public List<BacklogItemResponse> getByProjectId(long projectId) {
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
    public BacklogItemDetails getDetailsById(long id) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM, id);

        BacklogItem backlogItem = backlogItemRepository.findById(id)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info(RETURNING_RESPONSE_OF, backlogItem);

        return backlogItemMapper.backlogItemToBacklogItemDetails(backlogItem);
    }
}
