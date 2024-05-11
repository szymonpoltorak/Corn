import { Component, EventEmitter, Input, OnDestroy, Output } from '@angular/core';
import { MatGridList } from "@angular/material/grid-list";
import { Subject, take, takeUntil } from "rxjs";
import { Sprint } from "@interfaces/boards/backlog/sprint";
import { MatDialog } from "@angular/material/dialog";
import { BacklogEditFormComponent } from "@pages/boards/backlog/backlog-edit-form/backlog-edit-form.component";
import { SprintEditData } from "@interfaces/boards/backlog/sprint-edit-data.interfaces";
import { SprintService } from "@core/services/boards/backlog/sprint/sprint.service";


@Component({
    selector: 'app-day',
    standalone: true,
    imports: [
	MatGridList
    ],
    templateUrl: './day.component.html',
    styleUrl: './day.component.scss'
})
export class DayComponent implements OnDestroy {

    @Input() day: number = 0;
    @Input() isFirst: boolean = false;
    @Input() isLast: boolean = false;
    @Input() sprint: Sprint | null = null;
    @Input() backgroundColor: string = 'cyan';

    @Output() changeEmitter: EventEmitter<void> = new EventEmitter<void>();

    color_value: string = '500';

    constructor(private dialog: MatDialog,
		private sprintService: SprintService) {

    }


    private _eventEmitter: EventEmitter<boolean> = new EventEmitter<boolean>();

    hovered = false;

    $destroy: Subject<void> = new Subject<void>();

    @Input()
    set eventEmitter(emitter: EventEmitter<boolean> | null) {
	if (!emitter) {
	    return;
	}

	this._eventEmitter = emitter;

	this._eventEmitter
	    .pipe(takeUntil(this.$destroy))
	    .subscribe(value => {
		this.hovered = value;
		if (value) {
		    this.color_value = '300';
		} else {
		    this.color_value = '500';
		}
	    });
    }

    ngOnDestroy(): void {
	this.$destroy.next();
	this.$destroy.complete();
    }

    mouseOut(): void {
	this._eventEmitter.emit(false);
    }

    mouseOver(): void {
	this._eventEmitter.emit(true);
    }

    openSprint(): void {
	const dialogRef = this.dialog.open(BacklogEditFormComponent, {
	    enterAnimationDuration: '300ms',
	    exitAnimationDuration: '100ms',
	    data: this.sprint
	})

	dialogRef.afterClosed()
	    .pipe(take(1))
	    .subscribe((data: SprintEditData) => {
		if (!data || !this.sprint) {
		    return;
		}

                let changed: boolean = false;

		if (data.sprintName !== this.sprint.sprintName) {
                    changed = true;
		    this.sprintService.editSprintName(data.sprintName, this.sprint.sprintId)
			.pipe(take(1))
			.subscribe()
		}

		if (data.goal !== this.sprint.sprintDescription) {
                    changed = true;
		    this.sprintService.editSprintDescription(data.goal, this.sprint.sprintId)
			.pipe(take(1))
			.subscribe()
		}

                if (data.startDate !== this.sprint.startDate) {
                    changed = true;
                    this.sprintService.editSprintStartDate(data.startDate, this.sprint.sprintId)
                        .pipe(take(1))
                        .subscribe()
                }

                if (data.endDate !== this.sprint.endDate) {
                    changed = true;
                    this.sprintService.editSprintEndDate(data.endDate, this.sprint.sprintId)
                        .pipe(take(1))
                        .subscribe()
                }

                if (changed) {
                    this.changeEmitter.emit();
                }


	    })
    }


}
