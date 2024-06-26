import { Component, Input } from '@angular/core';
import { MatMenuModule } from '@angular/material/menu';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { BacklogItemType } from '@core/enum/BacklogItemType';
import { BacklogTypeComponent } from '@pages/boards/backlog/backlog-item-table/backlog-type/backlog-type.component';
import { ItemTypeChangedEvent } from '@core/interfaces/utils/change_item_type_menu/item_type_changed_event.interface';

@Component({
    selector: 'change-item-type-menu',
    standalone: true,
    imports: [
        CommonModule,
        MatMenuModule,
        MatButtonModule,
        BacklogTypeComponent,
    ],
    templateUrl: './change_item_type_menu.component.html',
})
export class ChangeItemTypeMenuComponent {

    @Input() itemId?: number;

    @Input() itemTypeChangedHandler?: (event: ItemTypeChangedEvent) => void;
    
    protected types: BacklogItemType[] = Object.values(BacklogItemType);

    protected changedType(type: BacklogItemType): void {
        this.itemTypeChangedHandler && this.itemId && this.itemTypeChangedHandler({
            itemId: this.itemId, 
            type: type
        });
    }

}
