package dev.corn.cornbackend.entities.backlog.comment.interfaces;

import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogItemCommentRepository extends JpaRepository<BacklogItemComment, Long> {
}
