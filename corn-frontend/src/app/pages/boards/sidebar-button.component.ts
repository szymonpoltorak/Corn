import { Component, Input } from '@angular/core';
import { MatIcon } from '@angular/material/icon';

@Component({
    selector: 'sidebar-button',
    standalone: true,
    imports: [
        MatIcon
    ],
    template: `

<button mat-basic-button (click)="click()" [class.bg-corn-primary-900]="selected"
    class="flex items-center justify-right py-2 space-x-2 w-full p-2 rounded-[4px]">
    <mat-icon>{{ iconName }}</mat-icon>
    <span [class.text-black]="selected">{{ label }}</span>
</button>

    `,
})
export class SidebarButton {
    @Input() iconName: string = '';
    @Input() label: string = '';
    @Input() click: () => void = () => { };
    @Input() selected: boolean = false;
}