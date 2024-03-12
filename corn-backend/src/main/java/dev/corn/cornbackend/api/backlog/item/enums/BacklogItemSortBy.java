package dev.corn.cornbackend.api.backlog.item.enums;

import lombok.Getter;

import static dev.corn.cornbackend.entities.backlog.item.constants.BacklogItemConstants.BACKLOG_ITEM_ASSIGNEE_FIELD_NAME;
import static dev.corn.cornbackend.entities.backlog.item.constants.BacklogItemConstants.BACKLOG_ITEM_STATUS_FIELD_NAME;
import static dev.corn.cornbackend.entities.backlog.item.constants.BacklogItemConstants.BACKLOG_ITEM_TYPE_FIELD_NAME;

@Getter
public enum BacklogItemSortBy {
    ASSIGNEE (BACKLOG_ITEM_ASSIGNEE_FIELD_NAME),
    ITEM_TYPE (BACKLOG_ITEM_TYPE_FIELD_NAME),
    STATUS (BACKLOG_ITEM_STATUS_FIELD_NAME);

    private final String value;
    BacklogItemSortBy(String value) {
        this.value = value;
    }

    public static BacklogItemSortBy of(String value) {
        return switch((value != null) ? value : BACKLOG_ITEM_STATUS_FIELD_NAME) {
            case BACKLOG_ITEM_ASSIGNEE_FIELD_NAME -> BacklogItemSortBy.ASSIGNEE;
            case BACKLOG_ITEM_TYPE_FIELD_NAME -> BacklogItemSortBy.ITEM_TYPE;
            default -> defaultValue();
        };
    }

    private static BacklogItemSortBy defaultValue() {
        return BacklogItemSortBy.STATUS;
    }
}
