package dev.corn.cornbackend.entities.sprint.interfaces;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
  
    @Query("SELECT s FROM Sprint s WHERE s.project.projectId = ?1")
    Page<Sprint> findAllByProjectId(long projectId, Pageable pageable);

    /**
     * Finds a Sprint by id and checks if the user is a member or the owner of the project associated
     * with the Sprint
     * @param id id of Sprint
     * @param user user requesting access
     * @return an Optional containing the found Sprint if it exists, empty Optional otherwise
     */
    @Query("SELECT s FROM Sprint s WHERE s.sprintId = :id AND (s.project.owner = :user OR :user IN (SELECT pm.user FROM ProjectMember pm WHERE pm.project = s.project))")
    Optional<Sprint> findByIdWithProjectMember(@Param("id") long id, @Param("user") User user);

    Optional<Sprint> findBySprintIdAndProject(long sprintId, Project project);

}
