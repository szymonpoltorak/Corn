package dev.corn.cornbackend.api.backlog.item;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemMapper;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemRepository;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberRepository;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintRepository;
import dev.corn.cornbackend.test.backlog.item.BacklogItemTestDataBuilder;
import dev.corn.cornbackend.test.backlog.item.data.AddBacklogItemTestData;
import dev.corn.cornbackend.test.backlog.item.data.EntityData;
import dev.corn.cornbackend.utils.exceptions.backlog.item.BacklogItemNotFoundException;
import dev.corn.cornbackend.utils.exceptions.project.ProjectDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.project.member.ProjectMemberDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.sprint.SprintNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private final static String SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE = "Should return correct BacklogItemResponse";
    private final static String SHOULD_THROW = "Should throw %s";

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

}