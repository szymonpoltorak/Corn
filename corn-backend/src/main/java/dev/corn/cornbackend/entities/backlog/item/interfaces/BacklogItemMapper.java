package dev.corn.cornbackend.entities.backlog.item.interfaces;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BacklogItemMapper {

    public BacklogItemResponse backlogItemToBacklogItemResponse(BacklogItem backlogItem);
}
