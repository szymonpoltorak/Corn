package dev.corn.cornbackend.entities.project.member.interfaces;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for ProjectMember entity
 */
@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    /**
     * Find all project members by project
     *
     * @param project  project
     * @param pageable pageable
     * @return page of project members
     */
    Page<ProjectMember> findAllByProject(Project project, Pageable pageable);

    /**
     * Find project member by project and user
     *
     * @param project project
     * @param user    user
     * @return project member
     */
    Optional<ProjectMember> findByProjectAndUser(Project project, User user);

    /**
     * Find project member by project member id and project
     *
     * @param projectMemberId project member id
     * @param project         project
     * @return project member
     */
    Optional<ProjectMember> findByProjectMemberIdAndProject(long projectMemberId, Project project);

    /**
     * Count project members by project id
     *
     * @param projectId project id
     * @return number of project members
     */
    long countByProjectProjectId(long projectId);

    /**
     * Find members by project id for project info
     *
     * @param projectId project id
     * @param pageable  pageable
     * @return page of project members
     */
    @Query("SELECT pm FROM ProjectMember pm WHERE pm.project.projectId = :projectId")
    Page<ProjectMember> findMembersByProjectIdForProjectInfo(long projectId, Pageable pageable);
}
