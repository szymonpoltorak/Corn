package dev.corn.cornbackend.entities.project.member.interfaces;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    Page<ProjectMember> findAllByProject(Project project, Pageable pageable);

    Optional<ProjectMember> findByProjectAndUser(Project project, User user);

    Optional<ProjectMember> findByProjectMemberIdAndProject(long projectMemberId, Project project);
}
