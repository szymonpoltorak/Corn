package dev.corn.cornbackend.api.backlog.item;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemMapper;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemRepository;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberRepository;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintRepository;
import dev.corn.cornbackend.test.backlog.item.BacklogItemTestDataBuilder;
import dev.corn.cornbackend.test.backlog.item.data.AddBacklogItemTestData;
import dev.corn.cornbackend.test.backlog.item.data.BacklogItemDetailsTestData;
import dev.corn.cornbackend.test.backlog.item.data.BacklogItemListTestData;
import dev.corn.cornbackend.test.backlog.item.data.EntityData;
import dev.corn.cornbackend.test.backlog.item.data.UpdateBacklogItemTestData;
import dev.corn.cornbackend.utils.exceptions.backlog.item.BacklogItemNotFoundException;
import dev.corn.cornbackend.utils.exceptions.project.ProjectDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.project.member.ProjectMemberDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.sprint.SprintNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private final UpdateBacklogItemTestData BACKLOG_ITEM_UPDATE_TEST_DATA = BacklogItemTestDataBuilder.updateBacklogItemTestData();
    private final BacklogItemListTestData BACKLOG_ITEM_LIST_TEST_DATA = BacklogItemTestDataBuilder.backlogItemListTestData();
    private final BacklogItemDetailsTestData BACKLOG_ITEM_DETAILS_TEST_DATA = BacklogItemTestDataBuilder.backlogItemDetailsTestData();

    private final static String SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE = "Should return correct BacklogItemResponse";
    private final static String SHOULD_THROW = "Should throw %s";
    private final static String SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE_LIST = "Should return correct list of BacklogItemResponses";

    @Test
    final void getById_shouldReturnBacklogItemResponseOnCorrectId() {
        //given
        long id = 1L;

        //when
        when(backlogItemRepository.findById(id))
                .thenReturn(Optional.of(BACKLOG_ITEM_TEST_DATA.backLogItem()));
        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_TEST_DATA.backLogItem()))
                .thenReturn(BACKLOG_ITEM_TEST_DATA.backlogItemResponse());

        BacklogItemResponse expected = BACKLOG_ITEM_TEST_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemServiceImpl.getById(id),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void getById_shouldThrowBacklogItemNotFoundExceptionOnIncorrectId() {
        //given
        long id = -1L;

        //when
        when(backlogItemRepository.findById(id))
                .thenReturn(Optional.empty());

        //then
        assertThrows(BacklogItemNotFoundException.class, () -> backlogItemServiceImpl.getById(id),
                String.format(SHOULD_THROW, BacklogItemNotFoundException.class.getSimpleName()));
    }

    @Test
    final void create_shouldReturnBacklogItemResponseOnCorrectData() {
        //given
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_TEST_DATA.backlogItemRequest();

        //when
        when(projectMemberRepository.findById(backlogItemRequest.projectMemberId()))
                .thenReturn(Optional.of(ENTITY_DATA.projectmember()));
        when(sprintRepository.findById(backlogItemRequest.sprintId()))
                .thenReturn(Optional.of(ENTITY_DATA.sprint()));
        when(projectRepository.findById(backlogItemRequest.projectId()))
                .thenReturn(Optional.of(ENTITY_DATA.project()));
        when(backlogItemRepository.save(any()))
                .thenReturn(BACKLOG_ITEM_TEST_DATA.backLogItem());
        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_TEST_DATA.backLogItem()))
                .thenReturn(BACKLOG_ITEM_TEST_DATA.backlogItemResponse());

        BacklogItemResponse expected = BACKLOG_ITEM_TEST_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemServiceImpl.create(backlogItemRequest),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void create_ShouldThrowProjectMemberNotFoundExceptionOnIncorrectProjectMember() {
        //given
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_TEST_DATA.backlogItemRequest();

        //when
        when(projectMemberRepository.findById(backlogItemRequest.projectMemberId()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(ProjectMemberDoesNotExistException.class, () -> backlogItemServiceImpl.create(backlogItemRequest),
                String.format(SHOULD_THROW, ProjectMemberDoesNotExistException.class.getSimpleName()));
    }

    @Test
    final void create_ShouldThrowSprintNotFoundExceptionOnIncorrectSprint() {
        //given
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_TEST_DATA.backlogItemRequest();

        //when
        when(projectMemberRepository.findById(backlogItemRequest.projectMemberId()))
                .thenReturn(Optional.of(ENTITY_DATA.projectmember()));
        when(sprintRepository.findById(backlogItemRequest.sprintId()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(SprintNotFoundException.class, () -> backlogItemServiceImpl.create(backlogItemRequest),
                String.format(SHOULD_THROW, SprintNotFoundException.class.getSimpleName()));
    }

    @Test
    final void create_ShouldThrowProjectNotFoundExceptionOnIncorrectProject() {
        //given
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_TEST_DATA.backlogItemRequest();

        //when
        when(projectMemberRepository.findById(backlogItemRequest.projectMemberId()))
                .thenReturn(Optional.of(ENTITY_DATA.projectmember()));
        when(sprintRepository.findById(backlogItemRequest.sprintId()))
                .thenReturn(Optional.of(ENTITY_DATA.sprint()));
        when(projectRepository.findById(backlogItemRequest.projectId()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(ProjectDoesNotExistException.class, () -> backlogItemServiceImpl.create(backlogItemRequest),
                String.format(SHOULD_THROW, ProjectDoesNotExistException.class.getSimpleName()));
    }

    @Test
    final void update_ShouldReturnCorrectBacklogItemResponseOnCorrectData() {
        //given
        long id = 1L;
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_UPDATE_TEST_DATA.backlogItemRequest();

        //when
        when(backlogItemRepository.findById(id))
                .thenReturn(Optional.of(BACKLOG_ITEM_UPDATE_TEST_DATA.backLogItem()));
        when(projectMemberRepository.findById(backlogItemRequest.projectMemberId()))
                .thenReturn(Optional.of(ENTITY_DATA.projectmember()));
        when(sprintRepository.findById(backlogItemRequest.sprintId()))
                .thenReturn(Optional.of(ENTITY_DATA.sprint()));
        when(projectRepository.findById(backlogItemRequest.projectId()))
                .thenReturn(Optional.of(ENTITY_DATA.project()));
        when(backlogItemRepository.save(BACKLOG_ITEM_UPDATE_TEST_DATA.updatedBacklogItem()))
                .thenReturn(BACKLOG_ITEM_UPDATE_TEST_DATA.updatedBacklogItem());
        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_UPDATE_TEST_DATA.updatedBacklogItem()))
                .thenReturn(BACKLOG_ITEM_UPDATE_TEST_DATA.backlogItemResponse());

        BacklogItemResponse expected = BACKLOG_ITEM_UPDATE_TEST_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemServiceImpl.update(id, backlogItemRequest),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void update_shouldThrowBacklogItemNotFoundExceptionOnIncorrectId() {
        //given
        long id = -1L;
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_UPDATE_TEST_DATA.backlogItemRequest();

        //when
        when(backlogItemRepository.findById(id))
                .thenReturn(Optional.empty());

        //then
        assertThrows(BacklogItemNotFoundException.class, () -> backlogItemServiceImpl.update(id, backlogItemRequest),
                String.format(SHOULD_THROW, BacklogItemNotFoundException.class.getSimpleName()));
    }

    @Test
    final void update_ShouldThrowProjectMemberNotFoundExceptionOnIncorrectProjectMember() {
        //given
        long id = 1L;
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_UPDATE_TEST_DATA.backlogItemRequest();

        //when
        when(backlogItemRepository.findById(id))
                .thenReturn(Optional.of(BACKLOG_ITEM_UPDATE_TEST_DATA.backLogItem()));
        when(projectMemberRepository.findById(backlogItemRequest.projectMemberId()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(ProjectMemberDoesNotExistException.class, () -> backlogItemServiceImpl.update(id, backlogItemRequest),
                String.format(SHOULD_THROW, ProjectMemberDoesNotExistException.class.getSimpleName()));
    }

    @Test
    final void update_shouldThrowSprintNotFoundExceptionOnIncorrectSprint() {
        //given
        long id = 1L;
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_UPDATE_TEST_DATA.backlogItemRequest();

        //when
        when(backlogItemRepository.findById(id))
                .thenReturn(Optional.of(BACKLOG_ITEM_UPDATE_TEST_DATA.backLogItem()));
        when(projectMemberRepository.findById(backlogItemRequest.projectMemberId()))
                .thenReturn(Optional.of(ENTITY_DATA.projectmember()));
        when(sprintRepository.findById(backlogItemRequest.sprintId()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(SprintNotFoundException.class, () -> backlogItemServiceImpl.update(id, backlogItemRequest),
                String.format(SHOULD_THROW, SprintNotFoundException.class.getSimpleName()));
    }

    @Test
    final void update_shouldThrowProjectNotFoundExceptionOnIncorrectProject() {
        //given
        long id = 1L;
        BacklogItemRequest backlogItemRequest = BACKLOG_ITEM_UPDATE_TEST_DATA.backlogItemRequest();

        //when
        when(backlogItemRepository.findById(id))
                .thenReturn(Optional.of(BACKLOG_ITEM_UPDATE_TEST_DATA.backLogItem()));
        when(projectMemberRepository.findById(backlogItemRequest.projectMemberId()))
                .thenReturn(Optional.of(ENTITY_DATA.projectmember()));
        when(sprintRepository.findById(backlogItemRequest.sprintId()))
                .thenReturn(Optional.of(ENTITY_DATA.sprint()));
        when(projectRepository.findById(backlogItemRequest.projectId()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(ProjectDoesNotExistException.class, () -> backlogItemServiceImpl.update(id, backlogItemRequest),
                String.format(SHOULD_THROW, ProjectDoesNotExistException.class.getSimpleName()));
    }

    @Test
    final void deleteById_shouldReturnCorrectBacklogItemResponseOnCorrectId() {
        //given
        long id = 1L;

        //when
        when(backlogItemRepository.findById(id))
                .thenReturn(Optional.of(BACKLOG_ITEM_TEST_DATA.backLogItem()));
        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_TEST_DATA.backLogItem()))
                .thenReturn(BACKLOG_ITEM_TEST_DATA.backlogItemResponse());

        BacklogItemResponse expected = BACKLOG_ITEM_TEST_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemServiceImpl.deleteById(id),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void deleteById_shouldThrowBacklogItemNotFoundExceptionOnIncorrectId() {
        //given
        long id = -1L;

        //when
        when(backlogItemRepository.findById(id))
                .thenReturn(Optional.empty());

        //then
        assertThrows(BacklogItemNotFoundException.class, () -> backlogItemServiceImpl.deleteById(id),
                String.format(SHOULD_THROW, BacklogItemNotFoundException.class.getSimpleName()));
    }

    @Test
    final void getBySprintId_shouldReturnCorrectBacklogItemListResponseOnCorrectId() {
        //given
        long id = 1L;

        //when
        when(sprintRepository.findById(id))
                .thenReturn(Optional.of(ENTITY_DATA.sprint()));
        when(backlogItemRepository.getBySprint(ENTITY_DATA.sprint()))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems());
        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems().get(0)))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses().get(0));
        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems().get(1)))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses().get(1));

        List<BacklogItemResponse> expected = BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses();

        //then
        assertEquals(expected, backlogItemServiceImpl.getBySprintId(id),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE_LIST);
    }

    @Test
    final void getBySprintId_shouldThrowSprintNotFoundExceptionOnIncorrectId() {
        //given
        long id = -1L;

        //when
        when(sprintRepository.findById(id))
                .thenReturn(Optional.empty());

        //then
        assertThrows(SprintNotFoundException.class, () -> backlogItemServiceImpl.getBySprintId(id),
                String.format(SHOULD_THROW, SprintNotFoundException.class.getSimpleName()));
    }

    @Test
    final void getByProjectId_shouldReturnCorrectBacklogItemListResponseOnCorrectId() {
        //given
        long id = 1L;

        //when
        when(projectRepository.findById(id))
                .thenReturn(Optional.of(ENTITY_DATA.project()));
        when(backlogItemRepository.getByProject(ENTITY_DATA.project()))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems());
        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems().get(0)))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses().get(0));
        when(backlogItemMapper.backlogItemToBacklogItemResponse(BACKLOG_ITEM_LIST_TEST_DATA.backlogItems().get(1)))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses().get(1));

        List<BacklogItemResponse> expected = BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses();

        //then
        assertEquals(expected, backlogItemServiceImpl.getByProjectId(id),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE_LIST);
    }

    @Test
    final void getByProjectId_shouldThrowProjectNotFoundExceptionOnIncorrectId() {
        //given
        long id = -1L;

        //when
        when(projectRepository.findById(id))
                .thenReturn(Optional.empty());

        //then
        assertThrows(ProjectDoesNotExistException.class, () -> backlogItemServiceImpl.getByProjectId(id),
                String.format(SHOULD_THROW, ProjectDoesNotExistException.class.getSimpleName()));
    }

    @Test
    final void getDetailsById_shouldReturnCorrectBacklogItemDetailsResponseOnCorrectId() {
        //given
        long id = 1L;

        //when
        when(backlogItemRepository.findById(id))
                .thenReturn(Optional.of(BACKLOG_ITEM_DETAILS_TEST_DATA.backlogItem()));
        when(backlogItemMapper.backlogItemToBacklogItemDetails(BACKLOG_ITEM_DETAILS_TEST_DATA.backlogItem()))
                .thenReturn(BACKLOG_ITEM_DETAILS_TEST_DATA.backlogItemDetails());

        BacklogItemDetails expected = BACKLOG_ITEM_DETAILS_TEST_DATA.backlogItemDetails();

        //then
        assertEquals(expected, backlogItemServiceImpl.getDetailsById(id),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void getDetailsById_shouldThrowBacklogItemNotFoundExceptionOnIncorrectId() {
        //given
        long id = -1L;

        //when
        when(backlogItemRepository.findById(id))
                .thenReturn(Optional.empty());

        //then
        assertThrows(BacklogItemNotFoundException.class, () -> backlogItemServiceImpl.getDetailsById(id),
                String.format(SHOULD_THROW, BacklogItemNotFoundException.class.getSimpleName()));
    }

}