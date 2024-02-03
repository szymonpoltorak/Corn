import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';

@Component({
    selector: 'userinfo',
    standalone: true,
    templateUrl: './userinfo.component.html',
    imports: [
        MatButtonModule, 
        MatMenuModule, 
        CommonModule, 
        MatIconModule,
        MatListModule
    ]
})
export class UserinfoComponent {

    @Input() avatarUrl: string = '';
    @Input() fullName: string = '';
    @Input() username: string = '';

    @Output() logout = new EventEmitter<void>();

}
