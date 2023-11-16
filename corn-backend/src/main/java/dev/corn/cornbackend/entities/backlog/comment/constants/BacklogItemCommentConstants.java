package dev.corn.cornbackend.entities.backlog.comment.constants;

public final class BacklogItemCommentConstants {
    public static final String BACKLOG_ITEM_COMMENT_COMMENT_BLANK_MSG = "Comment cannot be null and has to contain at least one non-whitespace character";
    public static final String BACKLOG_ITEM_COMMENT_COMMENT_WRONG_SIZE_MSG = "Comment must consist of max 500 characters";
    public static final String BACKLOG_ITEM_COMMENT_COMMENT_FIELD_NAME = "comment";

    public static final String BACKLOG_ITEM_COMMENT_USER_NULL_MSG = "User cannot be null";
    public static final String BACKLOG_ITEM_COMMENT_USER_FIELD_NAME = "user";

    public static final String BACKLOG_ITEM_COMMENT_BACKLOG_ITEM_NULL_MSG = "BackLogItem cannot be null";
    public static final String BACKLOG_ITEM_COMMENT_BACKLOG_ITEM_FIELD_NAME = "backlogItem";

    private BacklogItemCommentConstants() {

    }
}
