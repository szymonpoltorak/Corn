package dev.corn.cornbackend.entities.backlog.item.interfaces;

import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BacklogItemRepository extends JpaRepository<BacklogItem, Long> {

    List<BacklogItem> getBySprint(Sprint sprint);

    List<BacklogItem> getByProject(Project project);

    /**
     * Finds a BacklogItem by id and checks if the user is a member or the owner of the project associated
     * with the BacklogItem
     * @param id id of BacklogItem
     * @param user user requesting access
     * @return an Optional containing the found BacklogItem if it exists, empty Optional otherwise
     */
    @Query("SELECT b FROM BacklogItem b JOIN b.project p WHERE b.backlogItemId = :id AND (p.owner = :user OR :user IN (SELECT pm.user FROM ProjectMember pm WHERE pm.project = p))")
    Optional<BacklogItem> findByIdWithProjectMember(@Param("id") long id, @Param("user") User user);
}
