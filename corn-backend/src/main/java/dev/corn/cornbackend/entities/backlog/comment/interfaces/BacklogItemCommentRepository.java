package dev.corn.cornbackend.entities.backlog.comment.interfaces;

import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for BacklogItemComment
 */
@Repository
public interface BacklogItemCommentRepository extends JpaRepository<BacklogItemComment, Long> {
    /**
     * Finds a BacklogItemComment by id and user
     *
     * @param backlogItemCommentId id of BacklogItemComment
     * @param user user requesting access
     * @return an Optional containing the found BacklogItemComment if it exists, empty Optional otherwise
     */
    Optional<BacklogItemComment> findByBacklogItemCommentIdAndUser(long backlogItemCommentId, User user);

    /**
     * Finds a BacklogItemComment by id and checks if the user is the owner of the BacklogItemComment
     * or he is the owner of the project associated with the BacklogItemComment
     *
     * @param id id of BacklogItemComment
     * @param user user requesting access
     * @return an Optional containing the found BacklogItemComment if it exists, empty Optional otherwise
     */
    @Query("SELECT b FROM BacklogItemComment b WHERE b.backlogItemCommentId = :id AND (b.user = :user OR b.backlogItem.project.owner = :user)")
    Optional<BacklogItemComment> findByIdWithUserOrOwner(@Param ("id") long id, @Param("user") User user);

    /**
     * Finds a BacklogItemComment by id and checks if the user is a assignee or the owner of the project associated
     * with the BacklogItemComment
     * @param id id of BacklogItemComment
     * @param user user requesting access
     * @return an Optional containing the found BacklogItemComment if it exists, empty Optional otherwise
     */
    @Query("SELECT b FROM BacklogItemComment b WHERE b.backlogItemCommentId = :id AND (b.backlogItem.project.owner = :user OR :user IN (SELECT pm.user FROM ProjectMember pm WHERE pm.project = b.backlogItem.project))")
    Optional<BacklogItemComment> findByIdWithProjectMember(@Param ("id") long id, @Param("user") User user);

    /** Deletes all comments for given backlogItem
     *
     * @param backlogItem item to delete all comments for
     */
    void deleteByBacklogItem(BacklogItem backlogItem);

    /** Gets comments for given backlogItem, orders them by CommentDate ASC and pages them
     *
     * @param backlogItem item to find all comments for
     * @param pageable pageable to page comments by
     * @return list of found BacklogItemComments
     */
    Page<BacklogItemComment> getAllByBacklogItemOrderByCommentDateDesc(BacklogItem backlogItem, Pageable pageable);
}
