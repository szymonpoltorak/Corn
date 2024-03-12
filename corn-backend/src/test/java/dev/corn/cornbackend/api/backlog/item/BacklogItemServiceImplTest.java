package dev.corn.cornbackend.api.backlog.item;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponseList;
import dev.corn.cornbackend.api.backlog.item.enums.BacklogItemSortBy;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.backlog.item.enums.ItemStatus;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemMapper;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemRepository;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberRepository;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.test.backlog.item.BacklogItemTestDataBuilder;
import dev.corn.cornbackend.test.backlog.item.data.AddBacklogItemTestData;
import dev.corn.cornbackend.test.backlog.item.data.BacklogItemDetailsTestData;
import dev.corn.cornbackend.test.backlog.item.data.BacklogItemListTestData;
import dev.corn.cornbackend.test.backlog.item.data.EntityData;
import dev.corn.cornbackend.test.backlog.item.data.UpdateBacklogItemTestData;
import dev.corn.cornbackend.test.user.UserTestDataBuilder;
import dev.corn.cornbackend.utils.exceptions.backlog.item.BacklogItemNotFoundException;
import dev.corn.cornbackend.utils.exceptions.project.ProjectDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.project.member.ProjectMemberDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.sprint.SprintNotFoundException;
import dev.corn.cornbackend.utils.exceptions.utils.WrongPageNumberException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemServiceConstants.BACKLOG_ITEM_PAGE_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BacklogItemServiceImplTest {

    @InjectMocks
    private BacklogItemServiceImpl backlogItemServiceImpl;

    @Mock
    private BacklogItemRepository backlogItemRepository;
    @Mock
    private SprintRepository sprintRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMemberRepository projectMemberRepository;
    @Mock
    private  BacklogItemMapper backlogItemMapper;

    private final AddBacklogItemTestData BACKLOG_ITEM_TEST_DATA = BacklogItemTestDataBuilder.addBacklogItemTestData();
    private final EntityData ENTITY_DATA = BacklogItemTestDataBuilder.entityData();
    private final UpdateBacklogItemTestData BACKLOG_ITEM_UPDATE_TEST_DATA = BacklogItemTestDataBuilder
            .updateBacklogItemTestData(ItemStatus.IN_PROGRESS);

    private final UpdateBacklogItemTestData BACKLOG_ITEM_UPDATE_FINISH_DATA = BacklogItemTestDataBuilder
            .updateBacklogItemTestData(ItemStatus.DONE);
    private final BacklogItemListTestData BACKLOG_ITEM_LIST_TEST_DATA = BacklogItemTestDataBuilder.backlogItemListTestData();
    private final BacklogItemDetailsTestData BACKLOG_ITEM_DETAILS_TEST_DATA = BacklogItemTestDataBuilder.backlogItemDetailsTestData();

    private final static String SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE = "Should return correct BacklogItemResponse";
    private final static String SHOULD_THROW = "Should throw %s";
    private final static String SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE_LIST = "Should return correct list of BacklogItemResponses";
    private final static User SAMPLE_USER = UserTestDataBuilder.createSampleUser();

    @Test
    final void getById_shouldReturnBacklogItemResponseOnCorrectId() {
        //given
        long id = 1L;

        //when
        when(backlogItemRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.of(BACKLOG_ITEM_TEST_DATA.backLogItem()));

        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_TEST_DATA.backLogItem()))
                .thenReturn(BACKLOG_ITEM_TEST_DATA.backlogItemResponse());

        BacklogItemResponse expected = BACKLOG_ITEM_TEST_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemServiceImpl.getById(id, SAMPLE_USER),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void getById_shouldThrowBacklogItemNotFoundExceptionOnIncorrectId() {
        //given
        long id = -1L;

        //when
        when(backlogItemRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.empty());

        //then
        assertThrows(BacklogItemNotFoundException.class, () -> backlogItemServiceImpl.getById(id, SAMPLE_USER),
                String.format(SHOULD_THROW, BacklogItemNotFoundException.class.getSimpleName()));
    }

    @Test
    final void create_shouldReturnBacklogItemResponseOnCorrectData() {
        //given
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_TEST_DATA.backlogItemRequest();

        //when
        when(projectMemberRepository.findByProjectMemberIdAndProject(backlogItemRequest.projectMemberId(),
                ENTITY_DATA.project()))
                .thenReturn(Optional.of(ENTITY_DATA.projectmember()));

        when(sprintRepository.findBySprintIdAndProject(backlogItemRequest.sprintId(), ENTITY_DATA.project()))
                .thenReturn(Optional.of(ENTITY_DATA.sprint()));

        when(projectRepository.findByIdWithProjectMember(backlogItemRequest.projectId(), SAMPLE_USER))
                .thenReturn(Optional.of(ENTITY_DATA.project()));

        when(backlogItemRepository.save(any()))
                .thenReturn(BACKLOG_ITEM_TEST_DATA.backLogItem());

        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_TEST_DATA.backLogItem()))
                .thenReturn(BACKLOG_ITEM_TEST_DATA.backlogItemResponse());

        BacklogItemResponse expected = BACKLOG_ITEM_TEST_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemServiceImpl.create(backlogItemRequest, SAMPLE_USER),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void create_ShouldThrowProjectNotFoundExceptionOnIncorrectProject() {
        //given
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_TEST_DATA.backlogItemRequest();

        //when
        when(projectRepository.findByIdWithProjectMember(backlogItemRequest.projectId(), SAMPLE_USER))
                .thenReturn(Optional.empty());

        //then
        assertThrows(ProjectDoesNotExistException.class, () -> backlogItemServiceImpl.create(backlogItemRequest, SAMPLE_USER),
                String.format(SHOULD_THROW, ProjectDoesNotExistException.class.getSimpleName()));
    }

    @Test
    final void create_ShouldThrowProjectMemberNotFoundExceptionOnIncorrectProjectMember() {
        //given
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_TEST_DATA.backlogItemRequest();

        //when
        when(projectRepository.findByIdWithProjectMember(backlogItemRequest.projectId(), SAMPLE_USER))
                .thenReturn(Optional.of(ENTITY_DATA.project()));

        when(projectMemberRepository.findByProjectMemberIdAndProject(backlogItemRequest.projectMemberId(), ENTITY_DATA.project()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(ProjectMemberDoesNotExistException.class, () -> backlogItemServiceImpl.create(backlogItemRequest, SAMPLE_USER),
                String.format(SHOULD_THROW, ProjectMemberDoesNotExistException.class.getSimpleName()));
    }

    @Test
    final void create_ShouldThrowSprintNotFoundExceptionOnIncorrectSprint() {
        //given
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_TEST_DATA.backlogItemRequest();

        //when
        when(projectRepository.findByIdWithProjectMember(backlogItemRequest.projectId(), SAMPLE_USER))
                .thenReturn(Optional.of(ENTITY_DATA.project()));

        when(projectMemberRepository.findByProjectMemberIdAndProject(backlogItemRequest.projectMemberId(), ENTITY_DATA.project()))
                .thenReturn(Optional.of(ENTITY_DATA.projectmember()));

        when(sprintRepository.findBySprintIdAndProject(backlogItemRequest.sprintId(), ENTITY_DATA.project()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(SprintNotFoundException.class, () -> backlogItemServiceImpl.create(backlogItemRequest, SAMPLE_USER),
                String.format(SHOULD_THROW, SprintNotFoundException.class.getSimpleName()));
    }

    @Test
    final void update_ShouldReturnCorrectBacklogItemResponseOnCorrectData() {
        //given
        long id = 1L;
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_UPDATE_TEST_DATA.backlogItemRequest();

        //when
        when(backlogItemRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.of(BACKLOG_ITEM_UPDATE_TEST_DATA.backLogItem()));

        when(projectRepository.findByIdWithProjectMember(backlogItemRequest.projectId(), SAMPLE_USER))
                .thenReturn(Optional.of(ENTITY_DATA.project()));

        when(projectMemberRepository.findByProjectMemberIdAndProject(backlogItemRequest.projectMemberId(), ENTITY_DATA.project()))
                .thenReturn(Optional.of(ENTITY_DATA.projectmember()));

        when(sprintRepository.findBySprintIdAndProject(backlogItemRequest.sprintId(), ENTITY_DATA.project()))
                .thenReturn(Optional.of(ENTITY_DATA.sprint()));

        when(backlogItemRepository.save(BACKLOG_ITEM_UPDATE_TEST_DATA.updatedBacklogItem()))
                .thenReturn(BACKLOG_ITEM_UPDATE_TEST_DATA.updatedBacklogItem());

        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_UPDATE_TEST_DATA.updatedBacklogItem()))
                .thenReturn(BACKLOG_ITEM_UPDATE_TEST_DATA.backlogItemResponse());

        BacklogItemResponse expected = BACKLOG_ITEM_UPDATE_TEST_DATA.backlogItemResponse();

        BacklogItemResponse actual = backlogItemServiceImpl.update(id, backlogItemRequest, SAMPLE_USER);

        //then
        assertEquals(expected, actual, SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
        assertNull(actual.taskFinishDate(), "Task finish date should be null on DONE status");
    }

    @Test
    final void update_shouldReturnResponseWithTaskFinishDate() {
        //given
        long id = 1L;
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_UPDATE_FINISH_DATA.backlogItemRequest();

        //when
        when(backlogItemRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.of(BACKLOG_ITEM_UPDATE_FINISH_DATA.backLogItem()));

        when(projectRepository.findByIdWithProjectMember(backlogItemRequest.projectId(), SAMPLE_USER))
                .thenReturn(Optional.of(ENTITY_DATA.project()));

        when(projectMemberRepository.findByProjectMemberIdAndProject(backlogItemRequest.projectMemberId(), ENTITY_DATA.project()))
                .thenReturn(Optional.of(ENTITY_DATA.projectmember()));

        when(sprintRepository.findBySprintIdAndProject(backlogItemRequest.sprintId(), ENTITY_DATA.project()))
                .thenReturn(Optional.of(ENTITY_DATA.sprint()));

        when(backlogItemRepository.save(BACKLOG_ITEM_UPDATE_FINISH_DATA.updatedBacklogItem()))
                .thenReturn(BACKLOG_ITEM_UPDATE_FINISH_DATA.updatedBacklogItem());

        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_UPDATE_FINISH_DATA.updatedBacklogItem()))
                .thenReturn(BACKLOG_ITEM_UPDATE_FINISH_DATA.backlogItemResponse());

        BacklogItemResponse expected = BACKLOG_ITEM_UPDATE_FINISH_DATA.backlogItemResponse();

        BacklogItemResponse actual = backlogItemServiceImpl.update(id, backlogItemRequest, SAMPLE_USER);

        //then
        assertEquals(expected, actual, SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
        assertNotNull(actual.taskFinishDate(), "Task finish date should not be null on not DONE status");
    }

    @Test
    final void update_shouldThrowBacklogItemNotFoundExceptionOnIncorrectId() {
        //given
        long id = -1L;
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_UPDATE_TEST_DATA.backlogItemRequest();

        //when
        when(backlogItemRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.empty());

        //then
        assertThrows(BacklogItemNotFoundException.class, () -> backlogItemServiceImpl.update(id, backlogItemRequest, SAMPLE_USER),
                String.format(SHOULD_THROW, BacklogItemNotFoundException.class.getSimpleName()));
    }

    @Test
    final void update_ShouldThrowProjectMemberNotFoundExceptionOnIncorrectProjectMember() {
        //given
        long id = 1L;
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_UPDATE_TEST_DATA.backlogItemRequest();

        //when
        when(backlogItemRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.of(BACKLOG_ITEM_UPDATE_TEST_DATA.backLogItem()));

        when(projectRepository.findByIdWithProjectMember(backlogItemRequest.projectId(), SAMPLE_USER))
                .thenReturn(Optional.of(ENTITY_DATA.project()));

        when(projectMemberRepository.findByProjectMemberIdAndProject(backlogItemRequest.projectMemberId(), ENTITY_DATA.project()))
                .thenReturn(Optional.empty());


        //then
        assertThrows(ProjectMemberDoesNotExistException.class, () -> backlogItemServiceImpl.update(id, backlogItemRequest, SAMPLE_USER),
                String.format(SHOULD_THROW, ProjectMemberDoesNotExistException.class.getSimpleName()));
    }

    @Test
    final void update_shouldThrowSprintNotFoundExceptionOnIncorrectSprint() {
        //given
        long id = 1L;
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_UPDATE_TEST_DATA.backlogItemRequest();

        //when
        when(backlogItemRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.of(BACKLOG_ITEM_UPDATE_TEST_DATA.backLogItem()));

        when(projectRepository.findByIdWithProjectMember(backlogItemRequest.projectId(), SAMPLE_USER))
                .thenReturn(Optional.of(ENTITY_DATA.project()));

        when(projectMemberRepository.findByProjectMemberIdAndProject(backlogItemRequest.projectMemberId(), ENTITY_DATA.project()))
                .thenReturn(Optional.of(ENTITY_DATA.projectmember()));

        when(sprintRepository.findBySprintIdAndProject(backlogItemRequest.sprintId(), ENTITY_DATA.project()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(SprintNotFoundException.class, () -> backlogItemServiceImpl.update(id, backlogItemRequest, SAMPLE_USER),
                String.format(SHOULD_THROW, SprintNotFoundException.class.getSimpleName()));
    }

    @Test
    final void update_shouldThrowProjectNotFoundExceptionOnIncorrectProject() {
        //given
        long id = 1L;
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_UPDATE_TEST_DATA.backlogItemRequest();

        //when
        when(backlogItemRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.of(BACKLOG_ITEM_UPDATE_TEST_DATA.backLogItem()));

        when(projectRepository.findByIdWithProjectMember(backlogItemRequest.projectId(), SAMPLE_USER))
                .thenReturn(Optional.empty());

        //then
        assertThrows(ProjectDoesNotExistException.class, () -> backlogItemServiceImpl.update(id, backlogItemRequest, SAMPLE_USER),
                String.format(SHOULD_THROW, ProjectDoesNotExistException.class.getSimpleName()));
    }

    @Test
    final void deleteById_shouldReturnCorrectBacklogItemResponseOnCorrectId() {
        //given
        long id = 1L;

        //when
        when(backlogItemRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.of(BACKLOG_ITEM_TEST_DATA.backLogItem()));

        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_TEST_DATA.backLogItem()))
                .thenReturn(BACKLOG_ITEM_TEST_DATA.backlogItemResponse());

        BacklogItemResponse expected = BACKLOG_ITEM_TEST_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemServiceImpl.deleteById(id, SAMPLE_USER),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void deleteById_shouldThrowBacklogItemNotFoundExceptionOnIncorrectId() {
        //given
        long id = -1L;

        //when
        when(backlogItemRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.empty());

        //then
        assertThrows(BacklogItemNotFoundException.class, () -> backlogItemServiceImpl.deleteById(id, SAMPLE_USER),
                String.format(SHOULD_THROW, BacklogItemNotFoundException.class.getSimpleName()));
    }

    @Test
    final void getBySprintId_shouldReturnCorrectBacklogItemListResponseOnCorrectId() {
        //given
        long id = 1L;

        //when
        when(sprintRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.of(ENTITY_DATA.sprint()));

        when(backlogItemRepository.getBySprint(ENTITY_DATA.sprint()))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems());

        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems().get(0)))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses().get(0));

        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems().get(1)))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses().get(1));

        List<BacklogItemResponse> expected = BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses();

        //then
        assertEquals(expected, backlogItemServiceImpl.getBySprintId(id, SAMPLE_USER),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE_LIST);
    }

    @Test
    final void getBySprintId_shouldThrowSprintNotFoundExceptionOnIncorrectId() {
        //given
        long id = -1L;

        //when
        when(sprintRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.empty());

        //then
        assertThrows(SprintNotFoundException.class, () -> backlogItemServiceImpl.getBySprintId(id, SAMPLE_USER),
                String.format(SHOULD_THROW, SprintNotFoundException.class.getSimpleName()));
    }

    @Test
    final void getByProjectId_shouldReturnCorrectBacklogItemListResponseOnCorrectId() {
        //given
        long id = 1L;
        int pageNumber = 0;
        String sortBy = "status";
        String order = "ASC";
        Pageable pageable = PageRequest.of(pageNumber, 30, Sort.Direction.ASC, sortBy);

        //when
        when(projectRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.of(ENTITY_DATA.project()));

        when(backlogItemRepository.getByProject(ENTITY_DATA.project(), pageable))
                .thenReturn(new PageImpl<>(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems()));

        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems().get(0)))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses().get(0));

        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems().get(1)))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses().get(1));

        BacklogItemResponseList expected = BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponseList();

        //then
        assertEquals(expected, backlogItemServiceImpl.getByProjectId(id, pageNumber, sortBy, order, SAMPLE_USER),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE_LIST);
    }

    @Test
    final void getByProjectId_shouldThrowProjectNotFoundExceptionOnIncorrectId() {
        //given
        long id = -1L;
        int pageNumber = 0;
        String sortBy = "";
        String order = "";

        //when
        when(projectRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.empty());

        //then
        assertThrows(ProjectDoesNotExistException.class, () -> backlogItemServiceImpl.getByProjectId(id, pageNumber,
                        sortBy, order, SAMPLE_USER),
                String.format(SHOULD_THROW, ProjectDoesNotExistException.class.getSimpleName()));
    }

    @Test
    final void getByProjectId_shouldThrowExceptionWhenGivenPageNumberIsNegative() {
        //given
        long id = 1L;
        int pageNumber = -1;
        String sortBy = "";
        String order = "";

        //then
        assertThrows(WrongPageNumberException.class, () -> backlogItemServiceImpl.getByProjectId(
                id, pageNumber, sortBy, order, SAMPLE_USER),
                String.format(SHOULD_THROW, WrongPageNumberException.class.getSimpleName()));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"abab"})
    final void getByProjectId_shouldCallDatabaseWithDefaultValuesAndReturnCorrectBacklogItemsWhenGivenSortByOrOrderIsNullOrIncorrect(String value) {
        //given
        long id = 1L;
        int pageNumber = 0;
        Pageable pageable = PageRequest.of(pageNumber, BACKLOG_ITEM_PAGE_SIZE,
                Sort.by(Sort.DEFAULT_DIRECTION, BacklogItemSortBy.of(value).getValue()));

        //when
        when(projectRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.of(ENTITY_DATA.project()));

        when(backlogItemRepository.getByProject(ENTITY_DATA.project(), pageable))
                .thenReturn(new PageImpl<>(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems()));

        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems().get(0)))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses().get(0));

        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems().get(1)))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses().get(1));

        BacklogItemResponseList expected = BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponseList();

        //then
        assertEquals(expected, backlogItemServiceImpl.getByProjectId(id, pageNumber, value, value, SAMPLE_USER),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE_LIST);
    }

    @Test
    final void getDetailsById_shouldReturnCorrectBacklogItemDetailsResponseOnCorrectId() {
        //given
        long id = 1L;

        //when
        when(backlogItemRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.of(BACKLOG_ITEM_DETAILS_TEST_DATA.backlogItem()));

        when(backlogItemMapper.backlogItemToBacklogItemDetails(BACKLOG_ITEM_DETAILS_TEST_DATA.backlogItem()))
                .thenReturn(BACKLOG_ITEM_DETAILS_TEST_DATA.backlogItemDetails());

        BacklogItemDetails expected = BACKLOG_ITEM_DETAILS_TEST_DATA.backlogItemDetails();

        //then
        assertEquals(expected, backlogItemServiceImpl.getDetailsById(id, SAMPLE_USER),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void getDetailsById_shouldThrowBacklogItemNotFoundExceptionOnIncorrectId() {
        //given
        long id = -1L;

        //when
        when(backlogItemRepository.findByIdWithProjectMember(id, SAMPLE_USER))
                .thenReturn(Optional.empty());

        //then
        assertThrows(BacklogItemNotFoundException.class, () -> backlogItemServiceImpl.getDetailsById(id, SAMPLE_USER),
                String.format(SHOULD_THROW, BacklogItemNotFoundException.class.getSimpleName()));
    }

}