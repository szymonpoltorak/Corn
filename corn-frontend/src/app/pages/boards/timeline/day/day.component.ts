import { Component, Input } from '@angular/core';
import { MatGridList } from "@angular/material/grid-list";


@Component({
    selector: 'app-day',
    standalone: true,
    imports: [
        MatGridList
    ],
    templateUrl: './day.component.html',
    styleUrl: './day.component.scss'
})
export class DayComponent {

    @Input() day: number = 0;
    @Input() isFirst: boolean = false;
    @Input() isLast: boolean = false;
    @Input() sprintName: string = '';
    @Input() backgroundColor: string = 'cyan-400';

}
