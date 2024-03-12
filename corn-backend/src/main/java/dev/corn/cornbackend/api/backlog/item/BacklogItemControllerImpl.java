package dev.corn.cornbackend.api.backlog.item;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponseList;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.api.backlog.item.enums.BacklogItemSortBy;
import dev.corn.cornbackend.api.backlog.item.interfaces.BacklogItemController;
import dev.corn.cornbackend.api.backlog.item.interfaces.BacklogItemService;
import dev.corn.cornbackend.config.jwtprocessing.JwtAuthed;
import dev.corn.cornbackend.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemMappings.BACKLOG_ITEM_ADD_MAPPING;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemMappings.BACKLOG_ITEM_API_MAPPING;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemMappings.BACKLOG_ITEM_DELETE_MAPPING;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemMappings.BACKLOG_ITEM_GET_BY_PROJECT_MAPPING;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemMappings.BACKLOG_ITEM_GET_BY_SPRINT_MAPPING;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemMappings.BACKLOG_ITEM_GET_DETAILS_MAPPING;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemMappings.BACKLOG_ITEM_GET_MAPPING;
import static dev.corn.cornbackend.api.backlog.item.constants.BacklogItemMappings.BACKLOG_ITEM_UPDATE_MAPPING;

@RestController
@RequestMapping(BACKLOG_ITEM_API_MAPPING)
@RequiredArgsConstructor
public class BacklogItemControllerImpl implements BacklogItemController {

    private final BacklogItemService backlogItemService;

    @Override
    @GetMapping(BACKLOG_ITEM_GET_MAPPING)
    public final BacklogItemResponse getById(@RequestParam long id,
                                             @JwtAuthed User user) {
        return backlogItemService.getById(id, user);
    }

    @Override
    @PutMapping(BACKLOG_ITEM_UPDATE_MAPPING)
    public final BacklogItemResponse update(@RequestParam long id,
                                            @RequestBody BacklogItemRequest backlogItemRequest,
                                            @JwtAuthed User user) {
        return backlogItemService.update(id, backlogItemRequest, user);
    }

    @Override
    @DeleteMapping(BACKLOG_ITEM_DELETE_MAPPING)
    public final BacklogItemResponse deleteById(@RequestParam long id,
                                                @JwtAuthed User user) {
        return backlogItemService.deleteById(id, user);
    }

    @Override
    @PostMapping(BACKLOG_ITEM_ADD_MAPPING)
    public final BacklogItemResponse create(@RequestBody BacklogItemRequest backlogItemRequest,
                                            @JwtAuthed User user) {
        return backlogItemService.create(backlogItemRequest, user);
    }

    @Override
    @GetMapping(BACKLOG_ITEM_GET_BY_SPRINT_MAPPING)
    public final List<BacklogItemResponse> getBySprintId(@RequestParam long sprintId,
                                                         @JwtAuthed User user) {
        return backlogItemService.getBySprintId(sprintId, user);
    }

    @Override
    @GetMapping(BACKLOG_ITEM_GET_BY_PROJECT_MAPPING)
    public final BacklogItemResponseList getByProjectId(@RequestParam long projectId,
                                                        @RequestParam int pageNumber,
                                                        @RequestParam String sortBy,
                                                        @RequestParam String order,
                                                        @JwtAuthed User user) {
        return backlogItemService.getByProjectId(projectId, pageNumber, sortBy, order, user);
    }

    @Override
    @GetMapping(BACKLOG_ITEM_GET_DETAILS_MAPPING)
    public final BacklogItemDetails getDetailsById(@RequestParam long id,
                                                   @JwtAuthed User user) {
        return backlogItemService.getDetailsById(id, user);
    }
}
