package dev.corn.cornbackend.api.backlog.item;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.api.backlog.item.interfaces.BacklogItemController;
import dev.corn.cornbackend.api.backlog.item.interfaces.BacklogItemService;
import lombok.RequiredArgsConstructor;
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
    public BacklogItemResponse getById(@RequestParam long id) {
        return backlogItemService.getById(id);
    }

    @Override
    @PutMapping(BACKLOG_ITEM_UPDATE_MAPPING)
    public BacklogItemResponse update(@RequestParam long id,
                                      @RequestBody BacklogItemRequest backlogItemRequest) {
        return backlogItemService.update(id, backlogItemRequest);
    }

    @Override
    @DeleteMapping(BACKLOG_ITEM_DELETE_MAPPING)
    public BacklogItemResponse deleteById(@RequestParam long id) {
        return backlogItemService.deleteById(id);
    }

    @Override
    @PostMapping(BACKLOG_ITEM_ADD_MAPPING)
    public BacklogItemResponse create(@RequestBody BacklogItemRequest backlogItemRequest) {
        return backlogItemService.create(backlogItemRequest);
    }

    @Override
    @GetMapping(BACKLOG_ITEM_GET_BY_SPRINT_MAPPING)
    public List<BacklogItemResponse> getBySprintId(@RequestParam long sprintId) {
        return backlogItemService.getBySprintId(sprintId);
    }

    @Override
    @GetMapping(BACKLOG_ITEM_GET_BY_PROJECT_MAPPING)
    public List<BacklogItemResponse> getByProjectId(@RequestParam long projectId) {
        return backlogItemService.getByProjectId(projectId);
    }

    @Override
    @GetMapping(BACKLOG_ITEM_GET_DETAILS_MAPPING)
    public BacklogItemDetails getDetailsById(@RequestParam long id) {
        return backlogItemService.getDetailsById(id);
    }
}
