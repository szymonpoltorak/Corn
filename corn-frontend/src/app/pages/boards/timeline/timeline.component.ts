import { Component, OnInit } from '@angular/core';
import { DatePipe, NgForOf } from "@angular/common";
import { CommonModule } from '@angular/common';
import { MatIcon} from "@angular/material/icon";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { matArrowBack, matArrowForward } from "@ng-icons/material-icons/baseline";

interface Week {
    days: Day[]
}

interface Day {
    day: number | undefined,
    month: number
}

@Component({
    selector: 'app-timeline',
    standalone: true,
    imports: [
        DatePipe,
        NgForOf,
        CommonModule,
        MatIcon,
        NgIcon,
    ],
    templateUrl: './timeline.component.html',
    viewProviders: [provideIcons({
        matArrowForward,
        matArrowBack,
    })]
})
export class TimelineComponent {
    currentMonth: Date = new Date();
    days: string[] = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    weeks: Week[] = [];

    constructor() { }

    ngOnInit(): void {
        this.generateCalendar();
}
    generateCalendar(): void {
        const startDate = new Date(this.currentMonth.getFullYear(), this.currentMonth.getMonth(), 1);
        const endDate = new Date(this.currentMonth.getFullYear(), this.currentMonth.getMonth() + 1, 0);
        const numDays = endDate.getDate();
        const startDay = startDate.getDay();

        let dateCounter = 1;
        for (let i = 0; i < 6; i++) {
            const week : Week = {
                days: []
            };
            for (let j = 0; j < 7; j++) {
                if ((i === 0 && j < startDay) || dateCounter > numDays) {
                    week.days.push({ day: undefined, month: -1 });
                } else {
                    week.days.push({ day: dateCounter, month: this.currentMonth.getMonth() + 1 });
                    dateCounter++;
                }
            }
            this.weeks.push(week);
        }
    }

    prevMonth(): void {
        this.currentMonth = new Date(this.currentMonth.getFullYear(), this.currentMonth.getMonth() - 1);
        this.resetCalendar();
    }

    nextMonth(): void {
        this.currentMonth = new Date(this.currentMonth.getFullYear(), this.currentMonth.getMonth() + 1);
        this.resetCalendar();
    }

    resetCalendar(): void {
        this.weeks = [];
        this.generateCalendar();
    }
}