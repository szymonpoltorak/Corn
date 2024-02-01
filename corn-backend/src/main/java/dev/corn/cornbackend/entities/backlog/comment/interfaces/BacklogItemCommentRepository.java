package dev.corn.cornbackend.entities.backlog.comment.interfaces;

import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BacklogItemCommentRepository extends JpaRepository<BacklogItemComment, Long> {

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
     * Finds a BacklogItemComment by id and checks if the user is a member or the owner of the project associated
     * with the BacklogItemComment
     * @param id id of BacklogItemComment
     * @param user user requesting access
     * @return an Optional containing the found BacklogItemComment if it exists, empty Optional otherwise
     */
    @Query("SELECT b FROM BacklogItemComment b WHERE b.backlogItemCommentId = :id AND (b.backlogItem.project.owner = :user OR :user IN (SELECT pm.user FROM ProjectMember pm WHERE pm.project = b.backlogItem.project))")
    Optional<BacklogItemComment> findByIdWithProjectMember(@Param ("id") long id, @Param("user") User user);
}
