import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatIcon } from '@angular/material/icon';

@Component({
    selector: 'sidebar-button',
    standalone: true,
    imports: [
        MatIcon,
        CommonModule
    ],
    templateUrl: './sidebar-button.component.html'
})
export class SidebarButton {
    @Input() iconName: string = '';
    @Input() iconPath: string = '';
    @Input() label: string = '';
    @Input() click: () => void = () => { };
    @Input() selected: boolean = false;
}