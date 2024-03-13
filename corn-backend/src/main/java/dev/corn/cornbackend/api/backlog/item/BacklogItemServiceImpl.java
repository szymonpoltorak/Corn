package dev.corn.cornbackend.api.backlog.item;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponseList;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.api.backlog.item.enums.BacklogItemSortBy;
import dev.corn.cornbackend.api.backlog.item.interfaces.BacklogItemService;
import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.backlog.item.enums.ItemStatus;
import dev.corn.cornbackend.entities.backlog.item.enums.ItemType;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemMapper;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemRepository;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberRepository;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.utils.exceptions.backlog.item.BacklogItemNotFoundException;
import dev.corn.cornbackend.utils.exceptions.project.ProjectDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.project.member.ProjectMemberDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.sprint.SprintNotFoundException;
import dev.corn.cornbackend.utils.exceptions.utils.WrongPageNumberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.*;
import static dev.corn.cornbackend.entities.backlog.item.constants.BacklogItemConstants.*;
import static dev.corn.cornbackend.entities.project.member.constants.ProjectMemberConstants.PROJECT_MEMBER_USER_FIELD_NAME;
import static dev.corn.cornbackend.entities.user.constants.UserConstants.USER_NAME_FIELD_NAME;
import static dev.corn.cornbackend.entities.user.constants.UserConstants.USER_SURNAME_FIELD_NAME;


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
    public final BacklogItemResponse getById(long id, User user) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM, id);

        BacklogItem item = backlogItemRepository.findByIdWithProjectMember(id, user)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info(RETURNING_RESPONSE_OF, item);

        return backlogItemMapper.backlogItemToBacklogItemResponse(item);
    }

    @Override
    public final BacklogItemResponse update(long id, BacklogItemRequest backlogItemRequest, User user) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM, id);

        BacklogItem item = backlogItemRepository.findByIdWithProjectMember(id, user)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        BacklogItemBuilderDto builder = prepareDataForBacklogItemCreation(backlogItemRequest, user);

        BacklogItem newItem = buildUpdatedBacklogItem(id, builder, item.getComments(), backlogItemRequest);

        log.info(SAVING_AND_RETURNING_RESPONSE_OF, newItem);

        BacklogItem savedItem = backlogItemRepository.save(newItem);

        return backlogItemMapper.backlogItemToBacklogItemResponse(savedItem);
    }

    @Override
    public final BacklogItemResponse deleteById(long id, User user) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM, id);

        BacklogItem item = backlogItemRepository.findByIdWithProjectMember(id, user)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        backlogItemRepository.deleteById(id);

        log.info(RETURNING_RESPONSE_OF, item);

        return backlogItemMapper.backlogItemToBacklogItemResponse(item);
    }

    @Override
    public final BacklogItemResponse create(BacklogItemRequest backlogItemRequest, User user) {
        BacklogItemBuilderDto builder = prepareDataForBacklogItemCreation(backlogItemRequest, user);

        BacklogItem item = buildNewBacklogItem(backlogItemRequest.title(), backlogItemRequest.description(),
                backlogItemRequest.itemType(), builder);

        log.info(SAVING_AND_RETURNING_RESPONSE_OF, item);

        BacklogItem savedItem = backlogItemRepository.save(item);

        return backlogItemMapper.backlogItemToBacklogItemResponse(savedItem);
    }

    @Override
    public final List<BacklogItemResponse> getBySprintId(long sprintId, User user) {
        log.info(GETTING_BY_ID, SPRINT, sprintId);

        Sprint sprint = sprintRepository.findByIdWithProjectMember(sprintId, user)
                .orElseThrow(() -> new SprintNotFoundException(SPRINT_NOT_FOUND_MESSAGE));

        log.info("Getting backlog items for sprint: {}", sprint);

        List<BacklogItem> items = backlogItemRepository.getBySprint(sprint);

        log.info(RETURNING_BACKLOG_ITEMS_OF_QUANTITY, items.size());

        return items.stream()
                .map(backlogItemMapper::backlogItemToBacklogItemResponse)
                .toList();
    }

    @Override
    public final BacklogItemResponseList getByProjectId(long projectId, int pageNumber, String sortBy,
                                                        String order, User user) {
        if(pageNumber < 0) {
            throw new WrongPageNumberException(pageNumber);
        }

        BacklogItemSortBy sort = BacklogItemSortBy.of(sortBy);
        Sort.Direction direction = Sort.Direction.DESC.name().equalsIgnoreCase(order) ?
                Sort.Direction.DESC : Sort.DEFAULT_DIRECTION;

        return getByProjectId(projectId, pageNumber, sort, direction, user);
    }

    private BacklogItemResponseList getByProjectId(long projectId, int pageNumber, BacklogItemSortBy sortBy,
                                                 Sort.Direction order, User user) {
        log.info(GETTING_BY_ID, PROJECT, projectId);

        Project project = projectRepository.findByIdWithProjectMember(projectId, user)
                .orElseThrow(() -> new ProjectDoesNotExistException(PROJECT_NOT_FOUND_MESSAGE));

        Pageable pageRequest;

        if(sortBy == BacklogItemSortBy.ASSIGNEE) {
            pageRequest = getPageableForAssignee(pageNumber, order);
        } else {
            pageRequest = PageRequest.of(pageNumber, BACKLOG_ITEM_PAGE_SIZE, Sort.by(order,
                    sortBy.getValue()));
        }

        log.info(GETTING_BY_PROJECT, project, sortBy.getValue(), order);
        Page<BacklogItem> items = backlogItemRepository.getByProject(project, pageRequest);

        return BacklogItemResponseList.builder()
                .backlogItemResponseList(items.stream()
                        .map(backlogItemMapper::backlogItemToBacklogItemResponse)
                        .toList())
                .totalNumber(items.getTotalElements())
                .build();
    }

    @Override
    public final BacklogItemDetails getDetailsById(long id, User user) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM, id);

        BacklogItem backlogItem = backlogItemRepository.findByIdWithProjectMember(id, user)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info(RETURNING_RESPONSE_OF, backlogItem);

        return backlogItemMapper.backlogItemToBacklogItemDetails(backlogItem);
    }

    private record BacklogItemBuilderDto(Sprint sprint, Project project, ProjectMember assignee) {
    }

    private BacklogItemBuilderDto prepareDataForBacklogItemCreation(BacklogItemRequest backlogItemRequest, User user) {
        log.info(GETTING_BY_ID, PROJECT, backlogItemRequest.projectId());

        Project project = projectRepository.findByIdWithProjectMember(backlogItemRequest.projectId(), user)
                .orElseThrow(() -> new ProjectDoesNotExistException(PROJECT_NOT_FOUND_MESSAGE));

        log.info(GETTING_BY_ID, PROJECT_MEMBER, backlogItemRequest.projectMemberId());

        ProjectMember assignee = projectMemberRepository.findByProjectMemberIdAndProject(backlogItemRequest.projectMemberId(), project)
                .orElseThrow(() -> new ProjectMemberDoesNotExistException(PROJECT_MEMBER_NOT_FOUND_MESSAGE));

        log.info(GETTING_BY_ID, SPRINT, backlogItemRequest.sprintId());

        Sprint sprint = sprintRepository.findBySprintIdAndProject(backlogItemRequest.sprintId(), project)
                .orElseThrow(() -> new SprintNotFoundException(SPRINT_NOT_FOUND_MESSAGE));

        return new BacklogItemBuilderDto(sprint, project, assignee);
    }

    private BacklogItem buildUpdatedBacklogItem(long id, BacklogItemBuilderDto builder,
                                                List<BacklogItemComment> comments,
                                                BacklogItemRequest request) {
        return BacklogItem.builder()
                .backlogItemId(id)
                .title(request.title())
                .description(request.description())
                .comments(comments)
                .status(request.itemStatus())
                .itemType(request.itemType())
                .assignee(builder.assignee())
                .taskFinishDate(request.itemStatus() == ItemStatus.DONE ? LocalDate.now() : null)
                .sprint(builder.sprint())
                .project(builder.project())
                .build();
    }

    private BacklogItem buildNewBacklogItem(String title, String description, ItemType itemType, BacklogItemBuilderDto builder) {
        return BacklogItem.builder()
                .title(title)
                .description(description)
                .comments(Collections.emptyList())
                .status(ItemStatus.TODO)
                .itemType(itemType)
                .assignee(builder.assignee())
                .sprint(builder.sprint())
                .project(builder.project())
                .build();
    }


    private Pageable getPageableForAssignee(int pageNumber, Sort.Direction direction) {
        Sort sorting = Sort.by(
                new Sort.Order(direction, String.format("%s.%s.%s",
                        BACKLOG_ITEM_ASSIGNEE_FIELD_NAME,
                        PROJECT_MEMBER_USER_FIELD_NAME,
                        USER_SURNAME_FIELD_NAME)),
                new Sort.Order(direction, String.format("%s.%s.%s",
                        BACKLOG_ITEM_ASSIGNEE_FIELD_NAME,
                        PROJECT_MEMBER_USER_FIELD_NAME,
                        USER_NAME_FIELD_NAME))
        );

        return PageRequest.of(pageNumber, BACKLOG_ITEM_PAGE_SIZE, sorting);

    }
}
