import { BacklogItemStatus } from "@core/enum/BacklogItemStatus";
import { BacklogItemType } from "@core/enum/BacklogItemType";

export interface BacklogItemRequestPartialUpdate {
    title?: string;
    description?: string;
    projectMemberId?: number;
    sprintId?: number;
    projectId?: number;
    itemType?: BacklogItemType;
    itemStatus?: BacklogItemStatus;
}