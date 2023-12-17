package dev.corn.cornbackend.entities.sprint.interfaces;

import dev.corn.cornbackend.entities.sprint.Sprint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {


    @Query("SELECT s FROM Sprint s WHERE s.project.projectId = ?1")
    Page<Sprint> findAllByProjectId(long projectId, Pageable pageable);

}