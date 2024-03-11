package dev.corn.cornbackend.api.backlog.item.constants;

public final class BacklogItemServiceConstants {

    public static final String GETTING_BY_ID = "Getting {} by id: {}";
    public static final String BACKLOG_ITEM_NOT_FOUND_MESSAGE = "Backlog item not found";
    public static final String RETURNING_RESPONSE_OF = "Returning response of: {}";
    public static final String PROJECT_MEMBER_NOT_FOUND_MESSAGE = "Project assignee not found";
    public static final String SPRINT_NOT_FOUND_MESSAGE = "Sprint not found";
    public static final String PROJECT_NOT_FOUND_MESSAGE = "Project not found";
    public static final String BACKLOG_ITEM = "BacklogItem";
    public static final String PROJECT_MEMBER = "ProjectMember";
    public static final String SPRINT = "Sprint";
    public static final String PROJECT = "Project";
    public static final String RETURNING_BACKLOG_ITEMS_OF_QUANTITY = "Returning backlog items of quantity: {}";
    public static final String SAVING_AND_RETURNING_RESPONSE_OF = "Saving and returning response of: {}";
    public static final String GETTING_BY_PROJECT = "Getting backlog items for project: {}, sorting by: {}, order: {}";

    public static final String SORT_ASCENDING = "ASC";
    public static final String SORT_DESCENDING = "DESC";

    public static final int BACKLOG_ITEM_PAGE_SIZE = 30;


    private BacklogItemServiceConstants() {
    }

}
