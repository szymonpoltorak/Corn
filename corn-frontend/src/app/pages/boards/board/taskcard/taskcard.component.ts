import { Component, Input } from '@angular/core';
import {MatCardModule} from '@angular/material/card';

@Component({
    selector: 'app-taskcard',
    standalone: true,
    imports: [MatCardModule],
    templateUrl: './taskcard.component.html',
})
export class TaskcardComponent {
    
    @Input() content: string = '';
    @Input() taskid: string = '';
    @Input() assigneeAvatarUrl: string = '';
    @Input() assigneeName: string = '';

}
