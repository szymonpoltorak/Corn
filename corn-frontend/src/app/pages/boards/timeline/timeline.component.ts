import { Component, OnInit } from '@angular/core';
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
	providers: [provideIcons({matArrowLeft, matArrowRight})]
})
export class TimelineComponent implements OnInit{

	days: string[] = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];

	previousMonthDays: number[] = [0, 0];
	currentMonthDays: number = 0;
	nextMonthDays: number = 0;

	date: FormControl<_moment.Moment | null> = new FormControl(moment());

	setMonthAndYear(normalizedMonthAndYear: Moment, datepicker: MatDatepicker<Moment>): void {
		datepicker.close();

		const ctrlValue: Moment = this.date.value ?? moment();
		ctrlValue.month(normalizedMonthAndYear.month());
		ctrlValue.year(normalizedMonthAndYear.year());
		this.date.setValue(ctrlValue);

		this.setMonthDays(ctrlValue);
	}

	changeMonth(numberOfMonths: number): void {
		const ctrlValue: Moment = this.date.value!;
		ctrlValue.add(numberOfMonths, 'months');
		this.date.setValue(ctrlValue);

		this.setMonthDays(ctrlValue);
	}

	setMonthDays(value: Moment): void {
		const firstDayOfMonth = value.clone().startOf('month');

		const daysInCurrentMonth = value.daysInMonth();

		const daysFromPreviousMonth = firstDayOfMonth.day() === 0 ? 6 : firstDayOfMonth.day() - 1;

		const daysFromNextMonth = 7*6 - daysInCurrentMonth - daysFromPreviousMonth;

		if(daysFromPreviousMonth == 0) {
			this.previousMonthDays = [0, 0];
		} else {
			this.previousMonthDays = [firstDayOfMonth.clone().subtract(daysFromPreviousMonth, 'days').date(), firstDayOfMonth.clone().subtract(1, 'days').date()];
		}
		this.currentMonthDays = daysInCurrentMonth;
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
	}
}