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

/**
 * Repository for Project entity
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    /**
     * Finds all Projects by owner
     *
     * @param owner owner of Projects
     * @param pageable pagination information
     * @return a Page of Projects
     */
    Page<Project> findAllByOwnerOrderByName(User owner, Pageable pageable);

    /**
     * Finds a Project by id and checks if the user is a assignee or the owner of given project
     *
     * @param id id of Project
     * @param user user requesting access
     * @return an Optional containing the found Project if it exists, empty Optional otherwise
     */
    @Query("""
            SELECT p
            FROM Project p
            WHERE p.projectId = :id AND (p.owner = :user OR :user IN (SELECT pm.user FROM ProjectMember pm where pm.project = p))
            """)
    Optional<Project> findByIdWithProjectMember(@Param("id") long id, @Param("user") User user);

    /**
     * Finds a Project by id and owner
     *
     * @param projectId id of Project
     * @param owner owner of Project
     * @return an Optional containing the found Project if it exists, empty Optional otherwise
     */
    Optional<Project> findByProjectIdAndOwner(long projectId, User owner);

    /**
     * Checks if given User is already associated with Project of given id
     *
     * @param user user to check
     * @param projectId id of Project
     * @return true if User is owner or project assignee of given Project, false otherwise
     */
    @Query("""
            SELECT CASE WHEN COUNT (p) > 0 THEN true ELSE false END
            FROM Project p
            WHERE p.projectId = :projectId AND (p.owner = :user OR :user IN (SELECT pm.user FROM ProjectMember pm where pm.project = p))
            """)
    boolean existsByProjectMemberAndProjectId(@Param("user") User user, @Param("projectId") long projectId);
}
