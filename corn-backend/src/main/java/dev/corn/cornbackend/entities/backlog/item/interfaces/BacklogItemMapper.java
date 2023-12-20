package dev.corn.cornbackend.entities.backlog.item.interfaces;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = SprintMapper.class)
public interface BacklogItemMapper {

    BacklogItemResponse backlogItemToBacklogItemResponse(BacklogItem backlogItem);

    @Mapping(target = "sprint", source = "sprint")
    BacklogItemDetails backlogItemToBacklogItemDetails(BacklogItem backlogItem);
}
