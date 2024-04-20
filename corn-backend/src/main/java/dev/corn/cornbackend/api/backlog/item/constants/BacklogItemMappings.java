package dev.corn.cornbackend.api.backlog.item.constants;

public final class BacklogItemMappings {

    public static final String BACKLOG_ITEM_API_MAPPING = "/api/v1/backlog/item";
    public static final String BACKLOG_ITEM_ADD_MAPPING = "/add";
    public static final String BACKLOG_ITEM_UPDATE_MAPPING = "/update";
    public static final String BACKLOG_ITEM_DELETE_MAPPING = "/delete";
    public static final String BACKLOG_ITEM_GET_MAPPING = "/get";

    public static final String BACKLOG_ITEM_GET_BY_SPRINT_MAPPING = "/getBySprint";

    public static final String BACKLOG_ITEM_GET_BY_PROJECT_MAPPING = "/getByProject";

    public static final String BACKLOG_ITEM_GET_DETAILS_MAPPING = "/getDetails";

    public static final String BACKLOG_ITEM_GET_ALL_WITHOUT_SPRINT_MAPPING = "/getAllWithoutSprint";

    public static final String BACKLOG_ITEM_GET_ALL_BY_SPRINT_MAPPING = "/getAllBySprint";

    public static final String BACKLOG_ITEM_PARTIAL_UPDATE_MAPPING = "/partialUpdate";

    private BacklogItemMappings() {
    }
}
