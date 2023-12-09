package dev.corn.cornbackend.entities.sprint.interfaces;

import dev.corn.cornbackend.entities.sprint.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
}
