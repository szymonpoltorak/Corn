package dev.corn.cornbackend.repositories.backlog.item;

import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemRepository;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.repositories.backlog.item.data.BacklogItemRepositoryTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class BacklogItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BacklogItemRepository backlogItemRepository;

    private static BacklogItemRepositoryTestData TEST_DATA;

    @BeforeEach
    public void setUp() {
        TEST_DATA = BacklogItemRepositoryTestDataBuilder.backlogItemRepositoryTestData(entityManager);
    }
    @Test
    void test_findByIdWithProjectOwnerShouldReturnCorrectBacklogItem() {
        //given
        long id = TEST_DATA.backlogItem().getBacklogItemId();
        User user = TEST_DATA.owner();

        //when
        Optional<BacklogItem> backlogItem = backlogItemRepository.findByIdWithProjectMember(id, user);

        //then
        assertTrue(backlogItem.isPresent());
        assertEquals(TEST_DATA.backlogItem(), backlogItem.get());
    }

    @Test
    void test_findByIdWithProjectMemberShouldReturnCorrectBacklogItem() {
        //given
        long id = TEST_DATA.backlogItem().getBacklogItemId();
        User user = TEST_DATA.projectMember();

        //when
        Optional<BacklogItem> backlogItem = backlogItemRepository.findByIdWithProjectMember(id, user);

        //then
        assertTrue(backlogItem.isPresent());
        assertEquals(TEST_DATA.backlogItem(), backlogItem.get());
    }

    @Test
    void test_findByIdWithUserNotInProjectShouldReturnEmptyOptional() {
        //given
        long id = TEST_DATA.backlogItem().getBacklogItemId();
        User user = TEST_DATA.nonProjectMember();

        //when
        Optional<BacklogItem> backlogItem = backlogItemRepository.findByIdWithProjectMember(id, user);

        //then
        assertTrue(backlogItem.isEmpty());
    }

    @Test
    void test_getBySprintShouldReturnCorrectBacklogItems() {
        //given
        Sprint sprint = TEST_DATA.sprint();

        //when
        List<BacklogItem> backlogItems = backlogItemRepository.getBySprint(sprint);

        //then
        assertEquals(1, backlogItems.size());
        assertEquals(TEST_DATA.backlogItem(), backlogItems.get(0));
    }

    @Test
    void test_getByProjectShouldReturnCorrectBacklogItems() {
        //given
        dev.corn.cornbackend.entities.project.Project project = TEST_DATA.project();

        //when
        List<BacklogItem> backlogItems = backlogItemRepository.getByProject(project);

        //then
        assertEquals(1, backlogItems.size());
        assertEquals(TEST_DATA.backlogItem(), backlogItems.get(0));
    }
}
