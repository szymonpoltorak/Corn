package dev.corn.cornbackend.repositories.backlog.item;

import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemRepository;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.repositories.backlog.item.data.BacklogItemRepositoryTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class BacklogItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BacklogItemRepository backlogItemRepository;

    private static BacklogItemRepositoryTestData TEST_DATA = null;

    private static final String OPTIONAL_PRESENT = "Backlog item optional should be present";
    private static final String OPTIONAL_EMPTY = "Backlog item optional should be empty";
    private static final String BACKLOG_ITEM_EQUAL = "Backlog items should be equal";
    private static final String LIST_CORRECT_SIZE = "Backlog item list should have correct size";

    @BeforeEach
    public final void setUp() {
        TEST_DATA = BacklogItemRepositoryTestDataBuilder.backlogItemRepositoryTestData(entityManager);
    }
    @Test
    final void test_findByIdWithProjectOwnerShouldReturnCorrectBacklogItem() {
        //given
        long id = TEST_DATA.backlogItem().getBacklogItemId();
        User user = TEST_DATA.owner();

        //when
        Optional<BacklogItem> backlogItem = backlogItemRepository.findByIdWithProjectMember(id, user);

        //then
        assertTrue(backlogItem.isPresent(), OPTIONAL_PRESENT);
        assertEquals(TEST_DATA.backlogItem(), backlogItem.get(), BACKLOG_ITEM_EQUAL);
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnCorrectBacklogItem() {
        //given
        long id = TEST_DATA.backlogItem().getBacklogItemId();
        User user = TEST_DATA.projectMember();

        //when
        Optional<BacklogItem> backlogItem = backlogItemRepository.findByIdWithProjectMember(id, user);

        //then
        assertTrue(backlogItem.isPresent(), OPTIONAL_PRESENT);
        assertEquals(TEST_DATA.backlogItem(), backlogItem.get(), BACKLOG_ITEM_EQUAL);
    }

    @Test
    final void test_findByIdWithUserNotInProjectShouldReturnEmptyOptional() {
        //given
        long id = TEST_DATA.backlogItem().getBacklogItemId();
        User user = TEST_DATA.nonProjectMember();

        //when
        Optional<BacklogItem> backlogItem = backlogItemRepository.findByIdWithProjectMember(id, user);

        //then
        assertTrue(backlogItem.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_getBySprintShouldReturnCorrectBacklogItems() {
        //given
        Sprint sprint = TEST_DATA.sprint();
        PageRequest pageRequest = PageRequest.of(0, 1);

        //when
        Page<BacklogItem> backlogItems = backlogItemRepository.getBySprint(sprint, pageRequest);

        //then
        assertEquals(1, backlogItems.getTotalElements(), LIST_CORRECT_SIZE);
        assertEquals(TEST_DATA.backlogItem(), backlogItems.toList().get(0), BACKLOG_ITEM_EQUAL);
    }

    @Test
    final void test_getByProjectShouldReturnCorrectBacklogItems() {
        //given
        Project project = TEST_DATA.project();
        PageRequest pageRequest = PageRequest.of(0, 1);

        //when
        Page<BacklogItem> backlogItems = backlogItemRepository.getByProject(project, pageRequest);
        //then
        assertEquals(1L, backlogItems.getTotalElements(), LIST_CORRECT_SIZE);
        assertEquals(TEST_DATA.backlogItem(), backlogItems.toList().get(0), BACKLOG_ITEM_EQUAL);
    }
}
