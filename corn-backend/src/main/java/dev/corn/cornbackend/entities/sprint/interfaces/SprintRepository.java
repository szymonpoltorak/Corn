package dev.corn.cornbackend.entities.sprint.interfaces;

import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, Long> {

    Page<Sprint> findAllByOwnerOrderByName(User owner, Pageable pageable);

}
