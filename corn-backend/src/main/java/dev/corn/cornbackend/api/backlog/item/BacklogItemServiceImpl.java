package dev.corn.cornbackend.api.backlog.item;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponseList;
import dev.corn.cornbackend.api.backlog.item.enums.BacklogItemSortBy;
import dev.corn.cornbackend.api.backlog.item.interfaces.BacklogItemService;
import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.comment.interfaces.BacklogItemCommentRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.BACKLOG_ITEM;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.BACKLOG_ITEM_NOT_FOUND_MESSAGE;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.BACKLOG_ITEM_PAGE_SIZE;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.CREATING_PAGEABLE_FOR;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.GETTING_BACKLOG_ITEMS_WITH_PAGEABLE;
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
import static dev.corn.cornbackend.entities.backlog.item.constants.BacklogItemConstants.BACKLOG_ITEM_ASSIGNEE_FIELD_NAME;
import static dev.corn.cornbackend.entities.project.member.constants.ProjectMemberConstants.PROJECT_MEMBER_USER_FIELD_NAME;
import static dev.corn.cornbackend.entities.user.constants.UserConstants.USER_NAME_FIELD_NAME;
import static dev.corn.cornbackend.entities.user.constants.UserConstants.USER_SURNAME_FIELD_NAME;


@Service
@Slf4j
@RequiredArgsConstructor
public class BacklogItemServiceImpl implements BacklogItemService {

    private final BacklogItemRepository backlogItemRepository;
    private final BacklogItemCommentRepository backlogItemCommentRepository;
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final BacklogItemMapper backlogItemMapper;

    @Override
    public BacklogItemResponse getById(long id, User user) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM, id);

        BacklogItem item = backlogItemRepository.findByIdWithProjectMember(id, user)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info(RETURNING_RESPONSE_OF, item);

        return backlogItemMapper.backlogItemToBacklogItemResponse(item);
    }

    @Override
    public BacklogItemResponse update(long id, BacklogItemRequest backlogItemRequest, User user) {
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
    @Transactional
    public BacklogItemResponse deleteById(long id, User user) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM, id);

        BacklogItem item = backlogItemRepository.findByIdWithProjectMember(id, user)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        backlogItemCommentRepository.deleteByBacklogItem(item);

        backlogItemRepository.deleteById(id);

        log.info(RETURNING_RESPONSE_OF, item);

        return backlogItemMapper.backlogItemToBacklogItemResponse(item);
    }

    @Override
    public BacklogItemResponse create(BacklogItemRequest backlogItemRequest, User user) {
        BacklogItemBuilderDto builder = prepareDataForBacklogItemCreation(backlogItemRequest, user);

        BacklogItem item = buildNewBacklogItem(backlogItemRequest.title(), backlogItemRequest.description(),
                backlogItemRequest.itemType(), builder);

        log.info(SAVING_AND_RETURNING_RESPONSE_OF, item);

        BacklogItem savedItem = backlogItemRepository.save(item);

        return backlogItemMapper.backlogItemToBacklogItemResponse(savedItem);
    }

    @Override
    public BacklogItemResponseList getBySprintId(long sprintId, int pageNumber, String sortBy, String order, User user) {
        Pageable pageable = createPageableForBacklogItems(pageNumber, sortBy, order);

        log.info(GETTING_BY_ID, SPRINT, sprintId);

        Sprint sprint = sprintRepository.findByIdWithProjectMember(sprintId, user)
                .orElseThrow(() -> new SprintNotFoundException(SPRINT_NOT_FOUND_MESSAGE));

        log.info(GETTING_BACKLOG_ITEMS_WITH_PAGEABLE, SPRINT, sprint, pageable);
        Page<BacklogItem> items = backlogItemRepository.getBySprint(sprint, pageable);

        log.info(RETURNING_BACKLOG_ITEMS_OF_QUANTITY, items.getNumberOfElements());

        return BacklogItemResponseList.builder()
                .backlogItemResponseList(items.stream()
                        .map(backlogItemMapper::backlogItemToBacklogItemResponse)
                        .toList())
                .totalNumber(items.getTotalElements())
                .build();
    }

    @Override
    public BacklogItemResponseList getByProjectId(long projectId, int pageNumber, String sortBy,
                                                  String order, User user) {
        Pageable pageable = createPageableForBacklogItems(pageNumber, sortBy, order);

        log.info(GETTING_BY_ID, PROJECT, projectId);

        Project project = projectRepository.findByIdWithProjectMember(projectId, user)
                .orElseThrow(() -> new ProjectDoesNotExistException(PROJECT_NOT_FOUND_MESSAGE));


        log.info(GETTING_BACKLOG_ITEMS_WITH_PAGEABLE, PROJECT, project, pageable);
        Page<BacklogItem> items = backlogItemRepository.getByProject(project, pageable);

        log.info(RETURNING_BACKLOG_ITEMS_OF_QUANTITY, items.getNumberOfElements());

        return BacklogItemResponseList.builder()
                .backlogItemResponseList(items.stream()
                        .map(backlogItemMapper::backlogItemToBacklogItemResponse)
                        .toList())
                .totalNumber(items.getTotalElements())
                .build();
    }

    @Override
    public BacklogItemDetails getDetailsById(long id, User user) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM, id);

        BacklogItem backlogItem = backlogItemRepository.findByIdWithProjectMember(id, user)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info(RETURNING_RESPONSE_OF, backlogItem);

        return backlogItemMapper.backlogItemToBacklogItemDetails(backlogItem);
    }

    @Override
    public BacklogItemResponseList getAllWithoutSprint(long projectId, int pageNumber, String sortBy,
                                                       String order, User user) {
        Pageable pageable = createPageableForBacklogItems(pageNumber, sortBy, order);

        log.info(GETTING_BY_ID, PROJECT, projectId);

        Project project = projectRepository.findByIdWithProjectMember(projectId, user)
                .orElseThrow(() -> new ProjectDoesNotExistException(PROJECT_NOT_FOUND_MESSAGE));

        log.info("Getting backlog items that aren't assigned to any sprint for project: {}, pageable: {}",
                project, pageable);
        Page<BacklogItem> items = backlogItemRepository.findByProjectAndSprintIsNull(project, pageable);

        log.info(RETURNING_BACKLOG_ITEMS_OF_QUANTITY, items.getNumberOfElements());

        return BacklogItemResponseList.builder()
                .backlogItemResponseList(items.stream()
                        .map(backlogItemMapper::backlogItemToBacklogItemResponse)
                        .toList())
                .totalNumber(items.getTotalElements())
                .build();
    }

    @Override
    public List<BacklogItemResponse> getAllBySprintId(long sprintId, User user) {
        Pageable wholePage = Pageable.unpaged();

        log.info(GETTING_BY_ID, SPRINT, sprintId);

        Sprint sprint = sprintRepository.findByIdWithProjectMember(sprintId, user)
                .orElseThrow(() -> new SprintNotFoundException(SPRINT_NOT_FOUND_MESSAGE));

        log.info(GETTING_BACKLOG_ITEMS_WITH_PAGEABLE, SPRINT, sprint, wholePage);
        Page<BacklogItem> items = backlogItemRepository.getBySprint(sprint, wholePage);

        log.info(RETURNING_BACKLOG_ITEMS_OF_QUANTITY, items.getNumberOfElements());

        return items.stream()
                .map(backlogItemMapper::backlogItemToBacklogItemResponse)
                .toList();
    }

    @Override
    public BacklogItemResponse partialUpdate(long id, BacklogItemRequest request, User user) {
        log.info(GETTING_BY_ID, BACKLOG_ITEM, id);

        BacklogItem item = backlogItemRepository.findByIdWithProjectMember(id, user)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        setIfPresent(request::title, item::setTitle);
        setIfPresent(request::description, item::setDescription);
        setIfPresent(request::itemType, item::setItemType);
        setIfPresent(request::itemStatus, item::setStatus);

        ItemStatus itemStatus = request.itemStatus();
        if(itemStatus != null) {
            item.setTaskFinishDate(itemStatus == ItemStatus.DONE ? LocalDate.now() : null);
        }

        setResolvedIfPresent(request::projectId, presentId ->
                resolveProjectForUser(user, id), item::setProject);
        Project project = item.getProject();

        setResolvedIfPresent(request::projectMemberId, presentId ->
                resolveProjectMemberForProject(presentId, project), item::setAssignee);
        setResolvedIfPresent(request::sprintId, presentId ->
                resolveSprintForProject(presentId, project), item::setSprint);

        BacklogItem savedItem = backlogItemRepository.save(item);

        return backlogItemMapper.backlogItemToBacklogItemResponse(savedItem);
    }

    private record BacklogItemBuilderDto(Sprint sprint, Project project, ProjectMember assignee) {
    }

    private BacklogItemBuilderDto prepareDataForBacklogItemCreation(BacklogItemRequest backlogItemRequest, User user) {
        log.info(GETTING_BY_ID, PROJECT, backlogItemRequest.projectId());

        Project project = projectRepository.findByIdWithProjectMember(backlogItemRequest.projectId(), user)
                .orElseThrow(() -> new ProjectDoesNotExistException(PROJECT_NOT_FOUND_MESSAGE));

        ProjectMember assignee = null;

        if(backlogItemRequest.projectMemberId() != -1L) {
            log.info(GETTING_BY_ID, PROJECT_MEMBER, backlogItemRequest.projectMemberId());

            assignee = projectMemberRepository.findByProjectMemberIdAndProject(backlogItemRequest.projectMemberId(), project)
                    .orElseThrow(() -> new ProjectMemberDoesNotExistException(PROJECT_MEMBER_NOT_FOUND_MESSAGE));
        }

        Sprint sprint = null;

        if(backlogItemRequest.sprintId() != -1L) {
            log.info(GETTING_BY_ID, SPRINT, backlogItemRequest.sprintId());

            sprint = sprintRepository.findBySprintIdAndProject(backlogItemRequest.sprintId(), project)
                    .orElseThrow(() -> new SprintNotFoundException(SPRINT_NOT_FOUND_MESSAGE));
        }

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

    private Pageable createPageableForBacklogItems(int pageNumber, String sortBy, String order) {
        log.info(CREATING_PAGEABLE_FOR, pageNumber, sortBy, order);
        if(pageNumber < 0) {
            throw new WrongPageNumberException(pageNumber);
        }

        BacklogItemSortBy sort = BacklogItemSortBy.of(sortBy);
        Sort.Direction direction = Sort.Direction.DESC.name().equalsIgnoreCase(order) ?
                Sort.Direction.DESC : Sort.DEFAULT_DIRECTION;
        Pageable pageable;

        if(sort == BacklogItemSortBy.ASSIGNEE) {
            pageable = getPageableForAssignee(pageNumber, direction);
        } else {
            pageable = PageRequest.of(pageNumber, BACKLOG_ITEM_PAGE_SIZE, Sort.by(direction, sort.getValue()));
        }

        log.info("Returning pageable: {}", pageable);
        return pageable;
    }

    private <T, R> void setResolvedIfPresent(Supplier<T> getter, Function<T, R> resolver, Consumer<R> setter) {
        setIfPresent(getter, value -> setter.accept(resolver.apply(value)));
    }

    private <T> void setIfPresent(Supplier<T> getter, Consumer<T> setter) {
        Optional.ofNullable(getter.get()).ifPresent(setter);
    }

    private Project resolveProjectForUser(User user, Long projectId) {
        return projectRepository.findByIdWithProjectMember(projectId, user)
                .orElseThrow(() -> new ProjectDoesNotExistException(PROJECT_NOT_FOUND_MESSAGE));
    }

    private Sprint resolveSprintForProject(Long sprintId, Project project) {
        return sprintRepository.findBySprintIdAndProject(sprintId, project)
                .orElseThrow(() -> new SprintNotFoundException(SPRINT_NOT_FOUND_MESSAGE));
    }

    private ProjectMember resolveProjectMemberForProject(Long projectMemberId, Project project) {
        return projectMemberRepository.findByProjectMemberIdAndProject(projectMemberId, project)
                .orElseThrow(() -> new ProjectMemberDoesNotExistException(PROJECT_MEMBER_NOT_FOUND_MESSAGE));
    }

}
