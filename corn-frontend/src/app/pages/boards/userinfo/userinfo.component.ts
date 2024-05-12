import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { User } from '@core/interfaces/boards/user';
import { UserAvatarComponent } from '@pages/utils/user-avatar/user-avatar.component';

@Component({
    selector: 'userinfo',
    standalone: true,
    templateUrl: './userinfo.component.html',
    imports: [
        MatButtonModule, 
        MatMenuModule, 
        CommonModule, 
        MatIconModule,
        MatListModule,
        NgOptimizedImage,
        UserAvatarComponent,
    ]
})
export class UserinfoComponent {

    @Input() fullName: string = '';
    @Input() username: string = '';

    @Output() logout = new EventEmitter<void>();
    @Output() profileClicked = new EventEmitter<void>();

    protected taskAssigneeAsUser(): User | undefined {
        if (!this.fullName || !this.username) {
            return undefined;
        }
        return {
            userId: -1,
            name: this.fullName.split(" ")[0],
            surname: this.fullName.split(" ")[1],
            username: this.username,
        };
    }

}
