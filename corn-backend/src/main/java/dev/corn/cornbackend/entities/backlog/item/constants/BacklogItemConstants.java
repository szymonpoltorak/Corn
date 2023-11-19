package dev.corn.cornbackend.entities.backlog.item.constants;

public final class BacklogItemConstants {
    public static final String BACKLOG_ITEM_TITLE_BLANK_MSG = "Title cannot be null and has to contain at least one non-whitespace character";
    public static final String BACKLOG_ITEM_TITLE_WRONG_SIZE_MSG = "Title must consist of max 100 characters";
    public static final String BACKLOG_ITEM_TITLE_FIELD_NAME = "title";
    public static final int BACKLOG_ITEM_TITLE_MAX_SIZE = 100;

    public static final String BACKLOG_ITEM_DESCRIPTION_BLANK_MSG = "Title cannot be null and has to contain at least one non-whitespace character";
    public static final String BACKLOG_ITEM_DESCRIPTION_WRONG_SIZE_MSG = "Description must consist of max 500 characters";
    public static final String BACKLOG_ITEM_DESCRIPTION_FIELD_NAME = "description";
    public static final int BACKLOG_ITEM_DESCRIPTION_MAX_SIZE = 500;

    public static final String BACKLOG_ITEM_STATUS_NULL_MSG = "Item status cannot be null";
    public static final String BACKLOG_ITEM_STATUS_FIELD_NAME = "status";

    public static final String BACKLOG_ITEM_COMMENTS_NULL_ELEMENTS_MSG = "Comments cannot be null nor contain null elements";
    public static final String BACKLOG_ITEM_COMMENTS_FIELD_NAME = "comments";

    public static final String BACKLOG_ITEM_ASSIGNEE_NULL_MSG = "Assignee cannot be null";
    public static final String BACKLOG_ITEM_ASSIGNEE_FIELD_NAME = "assignee";

    public static final String BACKLOG_ITEM_SPRINT_NULL_MSG = "Sprint cannot be null";
    public static final String BACKLOG_ITEM_SPRINT_FIELD_NAME = "sprint";

    public static final String BACKLOG_ITEM_PROJECT_NULL_MSG = "Project cannot be null";
    public static final String BACKLOG_ITEM_PROJECT_FIELD_NAME = "project";

    private BacklogItemConstants() {

    }
}
