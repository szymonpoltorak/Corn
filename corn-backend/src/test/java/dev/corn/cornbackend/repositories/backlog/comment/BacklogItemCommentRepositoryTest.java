package dev.corn.cornbackend.repositories.backlog.comment;

import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.comment.interfaces.BacklogItemCommentRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.repositories.backlog.comment.data.BacklogItemCommentRepositoryTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class BacklogItemCommentRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    BacklogItemCommentRepository backlogItemCommentRepository;

    private static BacklogItemCommentRepositoryTestData TEST_DATA;

    @BeforeEach
    public void setUp() {
        TEST_DATA = BacklogItemCommentRepositoryTestDataBuilder.backlogItemCommentRepositoryTestData(testEntityManager);
    }

    @Test
    final void test_findByBacklogItemCommentIdAndUserShouldReturnCorrectBacklogItemCommentOnCorrectValues() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.commentOwner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByBacklogItemCommentIdAndUser(id, user);

        //then
        assertTrue(backlogItemComment.isPresent());
        assertEquals(TEST_DATA.comment(), backlogItemComment.get());
    }

    @Test
    final void test_findByBacklogItemCommentIdAndUserShouldReturnEmptyOptionalOnIncorrectId() {
        //given
        long id = 100L;
        User user = TEST_DATA.commentOwner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByBacklogItemCommentIdAndUser(id, user);

        //then
        assertTrue(backlogItemComment.isEmpty());
    }

    @Test
    final void test_findByBacklogItemCommentIdAndUserShouldReturnEmptyOptionalOnIncorrectUser() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.nonCommentOwner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByBacklogItemCommentIdAndUser(id, user);

        //then
        assertTrue(backlogItemComment.isEmpty());
    }

    @Test
    final void test_findByIdWithUserOrOwnerShouldReturnCorrectBacklogItemCommentOnCommentOwnerAndCorrectId() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.commentOwner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithUserOrOwner(id, user);

        //then
        assertTrue(backlogItemComment.isPresent());
        assertEquals(TEST_DATA.comment(), backlogItemComment.get());
    }

    @Test
    final void test_findByIdWithUserOrOwnerShouldReturnCorrectBacklogItemCommentOnProjectOwnerAndCorrectId() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.owner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithUserOrOwner(id, user);

        //then
        assertTrue(backlogItemComment.isPresent());
        assertEquals(TEST_DATA.comment(), backlogItemComment.get());
    }

    @Test
    final void test_findByIdWithUserOrOwnerShouldReturnEmptyOptionalOnIncorrectId() {
        //given
        long id = 100L;
        User user = TEST_DATA.commentOwner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithUserOrOwner(id, user);

        //then
        assertTrue(backlogItemComment.isEmpty());
    }

    @Test
    final void test_findByIdWithUserOrOwnerShouldReturnEmptyOptionalOnIncorrectUser() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.nonCommentOwner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithUserOrOwner(id, user);

        //then
        assertTrue(backlogItemComment.isEmpty());
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnCorrectBacklogItemCommentOnProjectOwnerAndCorrectId() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.owner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithProjectMember(id, user);

        //then
        assertTrue(backlogItemComment.isPresent());
        assertEquals(TEST_DATA.comment(), backlogItemComment.get());
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnCorrectBacklogItemCommentOnProjectMemberAndCorrectId() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.nonCommentOwner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithProjectMember(id, user);

        //then
        assertTrue(backlogItemComment.isPresent());
        assertEquals(TEST_DATA.comment(), backlogItemComment.get());
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnEmptyOptionalOnIncorrectId() {
        //given
        long id = 100L;
        User user = TEST_DATA.nonProjectMember();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithProjectMember(id, user);

        //then
        assertTrue(backlogItemComment.isEmpty());
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnEmptyOptionalOnIncorrectUser() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.nonProjectMember();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithProjectMember(id, user);

        //then
        assertTrue(backlogItemComment.isEmpty());
    }
}
