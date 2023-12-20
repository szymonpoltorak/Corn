package dev.corn.cornbackend.entities.backlog.item.interfaces;

import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.sprint.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BacklogItemRepository extends JpaRepository<BacklogItem, Long> {

    List<BacklogItem> getBySprint(Sprint sprint);

    List<BacklogItem> getByProject(Project project);
}
