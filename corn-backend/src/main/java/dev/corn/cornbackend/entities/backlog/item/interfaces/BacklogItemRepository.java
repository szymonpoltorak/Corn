package dev.corn.cornbackend.entities.backlog.item.interfaces;

import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for BacklogItem entities
 */
@Repository
public interface BacklogItemRepository extends JpaRepository<BacklogItem, Long> {
    /**
     * Finds all BacklogItems associated with a Sprint
     *
     * @param sprint Sprint to find BacklogItems for
     * @return List of BacklogItems associated with the Sprint
     */
    List<BacklogItem> getBySprint(Sprint sprint);

    /**
     * Finds all BacklogItems associated with a Project
     *
     * @param project Project to find BacklogItems for
     * @param pageable Pageable that pages and sorts the data
     * @return List of BacklogItems associated with the Project
     */
    Page<BacklogItem> getByProject(Project project, Pageable pageable);

    /**
     * Finds a BacklogItem by id and checks if the user is an assignee or the owner of the project associated
     * with the BacklogItem
     * @param id id of BacklogItem
     * @param user user requesting access
     * @return an Optional containing the found BacklogItem if it exists, empty Optional otherwise
     */
    @Query("SELECT b FROM BacklogItem b JOIN b.project p WHERE b.backlogItemId = :id AND (p.owner = :user OR :user IN (SELECT pm.user FROM ProjectMember pm WHERE pm.project = p))")
    Optional<BacklogItem> findByIdWithProjectMember(@Param("id") long id, @Param("user") User user);
}
