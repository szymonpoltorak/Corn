import { BacklogItemType } from "@core/enum/BacklogItemType";

export interface ItemTypeChangedEvent {
    itemId: number; 
    type: BacklogItemType;
}