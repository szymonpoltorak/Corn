package dev.corn.cornbackend.entities.backlog.comment.constants;

public final class BacklogItemCommentConstants {
    public static final String BACKLOG_ITEM_COMMENT_COMMENT_BLANK_MSG = "Comment cannot be null and has to contain at least one non-whitespace character";
    public static final String BACKLOG_ITEM_COMMENT_COMMENT_WRONG_SIZE_MSG = "Comment must consist of max 500 characters";
    public static final int BACKLOG_ITEM_COMMENT_MAX_SIZE = 500;
    public static final String BACKLOG_ITEM_COMMENT_COMMENT_FIELD_NAME = "comment";

    public static final String BACKLOG_ITEM_COMMENT_USER_NULL_MSG = "User cannot be null";
    public static final String BACKLOG_ITEM_COMMENT_USER_FIELD_NAME = "user";

    public static final String BACKLOG_ITEM_COMMENT_BACKLOG_ITEM_NULL_MSG = "BackLogItem cannot be null";
    public static final String BACKLOG_ITEM_COMMENT_BACKLOG_ITEM_FIELD_NAME = "backlogItem";

    public static final String BACKLOG_ITEM_COMMENT_COMMENT_TIME_NULL_MSG = "Comment time cannot be null";
    public static final String BACKLOG_ITEM_COMMENT_COMMENT_TIME_FIELD_NAME = "commentDate";

    public static final String BACKLOG_ITEM_COMMENT_LAST_EDIT_TIME_NULL_MSG = "Comment last edit time cannot be null";
    public static final String BACKLOG_ITEM_COMMENT_LAST_EDIT_TIME_FIELD_NAME = "lastEditTime";

    private BacklogItemCommentConstants() {

    }
}
