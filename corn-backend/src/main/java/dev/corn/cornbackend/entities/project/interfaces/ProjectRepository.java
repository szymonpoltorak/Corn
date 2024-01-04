package dev.corn.cornbackend.entities.project.interfaces;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findAllByOwnerOrderByName(User owner, Pageable pageable);

    /**
     * Finds a Project by id and checks if the user is a member or the owner of given project
     *
     * @param id id of Project
     * @param user user requesting access
     * @return an Optional containing the found Project if it exists, empty Optional otherwise
     */
    @Query("SELECT p FROM Project p WHERE p.projectId = :id AND (p.owner = :user OR :user IN (SELECT pm.user FROM ProjectMember pm where pm.project = p))")
    Optional<Project> findByIdWithProjectMember(@Param("id") long id, @Param("user") User user);

    Optional<Project> findByProjectIdAndOwner(long projectId, User owner);
}
