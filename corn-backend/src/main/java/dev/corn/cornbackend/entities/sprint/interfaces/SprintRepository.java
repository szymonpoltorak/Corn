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

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository for Sprint entity
 */
@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {

    /**
     * Finds all Sprints associated with Project of given projectId and checks if the user is an assignee
     * or the owner of the project
     *
     * @param projectId id of Project
     * @param user      user requesting access
     * @param pageable  pageable
     * @return Page containing sprints associated with Project if user's criteria are met, empty Page otherwise
     */
    @Query("""
            SELECT s FROM Sprint s
            WHERE s.project.projectId = :projectId AND
            (s.project.owner = :user OR :user IN (SELECT pm.user FROM ProjectMember pm WHERE pm.project = s.project))
            """)
    Page<Sprint> findAllByProjectId(@Param("projectId") long projectId, @Param("user") User user, Pageable pageable);

    /**
     * Finds a Sprint by id and checks if the user is a assignee or the owner of the project associated
     * with the Sprint
     *
     * @param id   id of Sprint
     * @param user user requesting access
     * @return an Optional containing the found Sprint if it exists, empty Optional otherwise
     */
    @Query("""
            SELECT s FROM Sprint s
            WHERE s.sprintId = :id AND
            (s.project.owner = :user OR :user IN (SELECT pm.user FROM ProjectMember pm WHERE pm.project = s.project))
            """)
    Optional<Sprint> findByIdWithProjectMember(@Param("id") long id, @Param("user") User user);

    /**
     * Finds a Sprint by id and by project
     *
     * @param sprintId id of Sprint
     * @param project  project
     * @return an Optional containing the found Sprint if it exists, empty Optional otherwise
     */
    Optional<Sprint> findBySprintIdAndProject(long sprintId, Project project);

    /**
     * Finds a Sprint by id and checks if the user is the owner of the project associated with the Sprint
     *
     * @param id   id of Sprint
     * @param user user requesting access
     * @return an Optional containing the found Sprint if it exists, empty Optional otherwise
     */
    @Query("SELECT s FROM Sprint s WHERE s.sprintId = :id AND s.project.owner = :user")
    Optional<Sprint> findByIdWithProjectOwner(@Param("id") long id, @Param("user") User user);

    /**
     * Checks if there is a Sprint with start date between specified dates
     *
     * @param startDate start date to check
     * @param endDate   end date to check
     * @param projectId id of Project
     * @return true if there is a Sprint with start date between specified dates, false otherwise
     */
    @Query("""
            select count(s) > 0 from Sprint s
            WHERE s.project.projectId = :projectId AND
            ((s.startDate <= :startDate AND s.endDate >= :startDate) OR
            (s.startDate <= :endDate AND s.endDate >= :endDate) OR
            (s.startDate >= :startDate AND s.endDate <= :endDate))
            """)
    boolean existsBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate, long projectId);

    /**
     * Checks if there is a Sprint with end date before specified date
     *
     * @param date date to check
     * @return true if there is a Sprint with end date before specified date, false otherwise
     */
    @Query("""
            select count(*) > 0 from Sprint s
            where s.endDate <= :date and s.startDate >= :date and s.sprintId != :sprintId
            """)
    boolean existsSprintPeriodWithGivenDate(LocalDate date, long sprintId);

    /**
     * Finds all sprints with given project that have end date after specified date and pages them
     *
     * @param project  project to find sprints associated with
     * @param date     date after which found sprints should end
     * @param pageable pageable to page and sort sprints
     * @return Page of found sprints
     */
    Page<Sprint> findAllByProjectAndEndDateAfter(Project project, LocalDate date, Pageable pageable);

    /**
     * Finds all sprints with given project that have start date after specified date and pages them
     *
     * @param project  project to find sprints associated with
     * @param date     date after which found sprints should start
     * @param pageable pageable to page and sort sprints
     * @return Page of found sprints
     */
    Page<Sprint> findAllByProjectAndStartDateAfter(Project project, LocalDate date, Pageable pageable);

    /**
     * Finds all sprints with given project that have end date before specified date and pages them
     *
     * @param project  project to find sprints associated with
     * @param date     date before which found sprints should end
     * @param pageable pageable to page and sort sprints
     * @return Page of found sprints
     */
    Page<Sprint> findAllByProjectAndEndDateBefore(Project project, LocalDate date, Pageable pageable);


}
