import { Component, EventEmitter, OnInit } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDatepicker, MatDatepickerModule } from '@angular/material/datepicker';

import _moment from 'moment';
import { default as _rollupMoment, Moment } from 'moment';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgIcon, provideIcons } from "@ng-icons/core";
import { matArrowLeft, matArrowRight } from "@ng-icons/material-icons/baseline";
import { MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatGridList, MatGridTile } from "@angular/material/grid-list";
import { DayComponent } from "@pages/boards/timeline/day/day.component";
import { SprintService } from "@core/services/boards/backlog/sprint/sprint.service";
import { take } from "rxjs";
import { Sprint } from "@interfaces/boards/backlog/sprint";
import { provideMomentDateAdapter } from "@angular/material-moment-adapter";

const moment = _rollupMoment || _moment;

export const MY_FORMATS = {
    parse: {
	dateInput: 'MM/YYYY',
    },
    display: {
	dateInput: 'MM/YYYY',
	monthYearLabel: 'MMM YYYY',
	dateA11yLabel: 'LL',
	monthYearA11yLabel: 'MMMM YYYY',
    },
};

/** @title Datepicker emulating a Year and month picker */
@Component({
    selector: 'app-timeline-component',
    templateUrl: 'timeline.component.html',
    standalone: true,
    imports: [
	MatFormFieldModule,
	MatInputModule,
	MatDatepickerModule,
	FormsModule,
	ReactiveFormsModule,
	MatIconButton,
	NgIcon,
	MatIcon,
	MatGridList,
	MatGridTile,
	DayComponent,
    ],
    providers: [provideIcons({matArrowLeft, matArrowRight}), provideMomentDateAdapter(MY_FORMATS)]
})
export class TimelineComponent implements OnInit {

    days: string[] = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];

    previousMonthDays: number[] = [0, 0];
    currentMonthDays: number = 0;
    nextMonthDays: number = 0;
    firstDay: Moment = moment().startOf('month');
    lastDay: Moment = moment().endOf('month');

    date: FormControl<_moment.Moment | null> = new FormControl(moment());

    sprints: Sprint[] = [];
    daysArray: number[] = [];
    indexArray: number[] = [];

    colors: string[] = [
	'red',
	'orange',
	'yellow',
	'lime',
	'green',
	'cyan',
	'blue',
	'purple',
	'pink'
    ]

    emitters: EventEmitter<boolean>[] = [];

    constructor(private sprintService: SprintService) {
    }

    setMonthAndYear(normalizedMonthAndYear: Moment, datepicker: MatDatepicker<Moment>): void {
	console.log(normalizedMonthAndYear);
	datepicker.close();

	const ctrlValue: Moment = this.date.value ?? moment();
	ctrlValue.month(normalizedMonthAndYear.month());
	ctrlValue.year(normalizedMonthAndYear.year());
	this.date.setValue(ctrlValue);

	this.setMonthDays(ctrlValue);

	this.getSprints();
    }

    changeMonth(numberOfMonths: number): void {
	const ctrlValue: Moment = this.date.value!;
	ctrlValue.add(numberOfMonths, 'months');
	this.date.setValue(ctrlValue);

	this.setMonthDays(ctrlValue);

	this.getSprints();
    }

    setMonthDays(value: Moment): void {
	const firstDayOfMonth: Moment = value.clone().startOf('month');

	const daysInCurrentMonth: number = value.daysInMonth();

	const daysFromPreviousMonth: number = firstDayOfMonth.day() === 0 ? 6 : firstDayOfMonth.day() - 1;

	const daysFromNextMonth: number = 7 * 6 - daysInCurrentMonth - daysFromPreviousMonth;

	if (daysFromPreviousMonth == 0) {
	    this.previousMonthDays = [0, 0];
	    this.firstDay = firstDayOfMonth.clone();
	} else {
	    this.firstDay = firstDayOfMonth.clone().subtract(daysFromPreviousMonth, 'days');
	    this.previousMonthDays = [this.firstDay.date(), firstDayOfMonth.clone().subtract(1, 'days').date()];
	}
	this.currentMonthDays = daysInCurrentMonth;
	this.lastDay = firstDayOfMonth.clone().endOf('month').add(daysFromNextMonth, 'days');
	this.nextMonthDays = daysFromNextMonth;
    }

    getArrayFromDays(firstDay: number, lastDay: number): number[] {
	if(firstDay == 0) {
	    return [];
	}

	return Array.from({length: lastDay - firstDay + 1}, (_, i) => firstDay + i);
    }

    ngOnInit(): void {
	this.setMonthDays(this.date.value!);

	this.getSprints();
    }

    getSprints(): void {
	this.sprintService.getSprintsBetweenDates(this.firstDay, this.lastDay)
	    .pipe(take(1))
	    .subscribe(sprints => {
		this.sprints = sprints;
		this.createDayArray();
		this.createIndexArray();
		this.createEmittersArray();
	    });
    }

    createEmittersArray(): void {
	let emitters: EventEmitter<boolean>[] = [];

	for(let i = 0; i < this.sprints.length; i++) {
	    emitters.push(new EventEmitter<boolean>());
	}

	this.emitters = emitters;
    }

    createDayArray(): void {
	let days: number[] = [];

	days = [...days, ...this.getArrayFromDays(this.previousMonthDays[0], this.previousMonthDays[1])]
	days = [...days, ...this.getArrayFromDays(1, this.currentMonthDays)];
	days = [...days, ...this.getArrayFromDays(1, this.nextMonthDays)];

	this.daysArray = days;
    }

    createIndexArray(): void {
	let days: number[] = [];

	if(this.sprints.length == 0) {
	    days = Array.from({length: 42}, (_, i) => -1);
	    this.indexArray = days;
	    return;
	}

	let difference: number = moment(this.sprints[0].startDate).diff(this.firstDay, 'days');

	for(let i = 0; i < difference; i++) {
	    days.push(-1);
	}

	let previousSprintLastDay: Moment | null = null;

	for(let j = 0; j < this.sprints.length; j++) {
	    let sprint: Sprint = this.sprints[j];
	    let startDate: Moment = moment(sprint.startDate);

	    if(startDate.isBefore(this.firstDay)) {
		startDate = this.firstDay;
	    }

	    if(previousSprintLastDay) {
		difference = startDate.diff(previousSprintLastDay, 'days');
		difference -= 1;

		for (let i = 0; i < difference; i++) {
		    days.push(-1);
		}
	    }

	    let endDate: Moment = moment(sprint.endDate);

	    if(endDate.isAfter(this.lastDay)) {
		endDate = this.lastDay;
	    }

	    difference = endDate.diff(startDate, 'days');

	    for(let i = 0; i <= difference; i++) {
		days.push(j);
	    }

	    previousSprintLastDay = endDate;
	}

	difference = this.lastDay.diff(previousSprintLastDay, 'days');

	for(let i = 0; i < difference; i++) {
	    days.push(-1);
	}

	this.indexArray = days;
    }

}