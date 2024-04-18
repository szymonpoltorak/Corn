package dev.corn.cornbackend.repositories.backlog.comment;

import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.comment.interfaces.BacklogItemCommentRepository;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.repositories.backlog.comment.data.BacklogItemCommentRepositoryTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class BacklogItemCommentRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BacklogItemCommentRepository backlogItemCommentRepository;

    private static BacklogItemCommentRepositoryTestData TEST_DATA = null;

    private static final String OPTIONAL_PRESENT = "Backlog item comment optional should be present";
    private static final String OPTIONAL_EMPTY = "Backlog item comment optional should be empty";
    private static final String BACKLOG_ITEM_COMMENT_EQUAL = "Backlog item comments should be equal";

    @BeforeEach
    public final void setUp() {
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
        assertTrue(backlogItemComment.isPresent(), OPTIONAL_PRESENT);
        assertEquals(TEST_DATA.comment(), backlogItemComment.get(), BACKLOG_ITEM_COMMENT_EQUAL);
    }

    @Test
    final void test_findByBacklogItemCommentIdAndUserShouldReturnEmptyOptionalOnIncorrectId() {
        //given
        long id = 100L;
        User user = TEST_DATA.commentOwner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByBacklogItemCommentIdAndUser(id, user);

        //then
        assertTrue(backlogItemComment.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findByBacklogItemCommentIdAndUserShouldReturnEmptyOptionalOnIncorrectUser() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.nonCommentOwner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByBacklogItemCommentIdAndUser(id, user);

        //then
        assertTrue(backlogItemComment.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findByIdWithUserOrOwnerShouldReturnCorrectBacklogItemCommentOnCommentOwnerAndCorrectId() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.commentOwner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithUserOrOwner(id, user);

        //then
        assertTrue(backlogItemComment.isPresent(), OPTIONAL_PRESENT);
        assertEquals(TEST_DATA.comment(), backlogItemComment.get(), BACKLOG_ITEM_COMMENT_EQUAL);
    }

    @Test
    final void test_findByIdWithUserOrOwnerShouldReturnCorrectBacklogItemCommentOnProjectOwnerAndCorrectId() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.owner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithUserOrOwner(id, user);

        //then
        assertTrue(backlogItemComment.isPresent(), OPTIONAL_PRESENT);
        assertEquals(TEST_DATA.comment(), backlogItemComment.get(), BACKLOG_ITEM_COMMENT_EQUAL);
    }

    @Test
    final void test_findByIdWithUserOrOwnerShouldReturnEmptyOptionalOnIncorrectId() {
        //given
        long id = 100L;
        User user = TEST_DATA.commentOwner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithUserOrOwner(id, user);

        //then
        assertTrue(backlogItemComment.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findByIdWithUserOrOwnerShouldReturnEmptyOptionalOnIncorrectUser() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.nonCommentOwner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithUserOrOwner(id, user);

        //then
        assertTrue(backlogItemComment.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnCorrectBacklogItemCommentOnProjectOwnerAndCorrectId() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.owner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithProjectMember(id, user);

        //then
        assertTrue(backlogItemComment.isPresent(), OPTIONAL_PRESENT);
        assertEquals(TEST_DATA.comment(), backlogItemComment.get(), BACKLOG_ITEM_COMMENT_EQUAL);
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnCorrectBacklogItemCommentOnProjectMemberAndCorrectId() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.nonCommentOwner();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithProjectMember(id, user);

        //then
        assertTrue(backlogItemComment.isPresent(), OPTIONAL_PRESENT);
        assertEquals(TEST_DATA.comment(), backlogItemComment.get(), BACKLOG_ITEM_COMMENT_EQUAL);
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnEmptyOptionalOnIncorrectId() {
        //given
        long id = 100L;
        User user = TEST_DATA.nonProjectMember();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithProjectMember(id, user);

        //then
        assertTrue(backlogItemComment.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnEmptyOptionalOnIncorrectUser() {
        //given
        long id = TEST_DATA.comment().getBacklogItemCommentId();
        User user = TEST_DATA.nonProjectMember();

        //when
        Optional<BacklogItemComment> backlogItemComment = backlogItemCommentRepository.findByIdWithProjectMember(id, user);

        //then
        assertTrue(backlogItemComment.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_getAllByBacklogItemOrderByCommentDateDescShouldReturnCorrectPageOfComments() {
        //given
        BacklogItem backlogItem = TEST_DATA.backlogItemWithComment();
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<BacklogItemComment> comments = backlogItemCommentRepository.getAllByBacklogItemOrderByCommentDateDesc(backlogItem, pageable);

        //then
        assertEquals(1, comments.getNumberOfElements(), "Should return page of correct number of elements");
        assertEquals(TEST_DATA.comment(), comments.toList().get(0), "Page should contain correct comment");
    }
}
