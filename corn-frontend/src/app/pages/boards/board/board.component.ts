import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { SlicesModelService } from '@core/services/boards/board/slice/slices-model.service';
import { SliceService } from '@core/services/boards/board/slice/slice.service';
import { SliceComponent } from './slice/slice.component';
import { BoardModelService } from '@core/services/boards/board/model.service';
import { MatIconModule } from '@angular/material/icon';
import { ASSIGNEES, TASKS } from './placeholder-data';
import { Task } from '@core/interfaces/boards/board/task.interface';
import { Assignee } from '@core/interfaces/boards/board/assignee.interface';
import { TaskGrouping } from '@core/enum/boards/board/task-grouping';
import { ColumnSetLayout } from './layout/column-set-layout.component';
import { GroupingMetadata } from '@core/types/board/boards/grouping-metadata';
import { TaskGrouper } from '@core/types/board/boards/task-grouper';
import { TaskChangedGroupEvent } from '@interfaces/boards/board/task-changed-group-event.interface';
import { TaskChangedColumnEvent } from '@interfaces/boards/board/task-changed-column-event.interface';
import { Hideable } from '@core/interfaces/boards/board/hideable.interface';

@Component({
    selector: 'app-board',
    standalone: true,
    imports: [
        CommonModule,
        MatInputModule,
        MatMenuModule,
        MatButtonModule,
        SliceComponent,
        MatIconModule,
        ColumnSetLayout,
    ],
    providers: [
        BoardModelService,
        SlicesModelService,
        SliceService,
    ],
    templateUrl: './board.component.html',
})
export class BoardComponent implements OnInit {

    protected filterString: string = "";
    protected taskGrouping: TaskGrouping = TaskGrouping.NONE;

    constructor(
        protected readonly modelService: BoardModelService,
        protected readonly slicesModelService: SlicesModelService<GroupingMetadata>,
    ) { }

    ngOnInit(): void {
        this.modelService.modelChangeHandler = () => {
            this.slicesModelService.rebuildSlices();
        }
        this.modelService.assignees = Object.values(ASSIGNEES);
        this.modelService.todo = TASKS.TODO;
        this.modelService.inprogress = TASKS.INPROGRESS;
        this.modelService.done = TASKS.DONE;
        this.slicesModelService.groupChangedHandler = (event: TaskChangedGroupEvent) => {
            if (this.taskGrouping == TaskGrouping.BY_ASSIGNEE &&
                event.sourceGroupMetadata != event.destinationGroupMetadata
            ) {
                this.modelService.setAssigneeForTask(event.task, event.destinationGroupMetadata);
            }
        }
        this.slicesModelService.columnChangedHandler = (event: TaskChangedColumnEvent) => {
            this.modelService.moveTaskToArray(event.task,
                event.sourceColumn, event.sourceColumnIndex,
                event.destinationColumn, event.destinationColumnIndex
            );
        }
        this.updateFilterString(this.filterString);
        this.updateTaskGrouping(this.taskGrouping);
    }

    protected updateFilterString(filterString: string): void {
        filterString = filterString.toLowerCase();
        this.filterString = filterString;
        const stringPredicate = (s: string) => s.toLowerCase().includes(filterString);
        this.slicesModelService.filter = (t: Task) => {
            return stringPredicate(t.content)
                || stringPredicate(t.assignee.firstName)
                || stringPredicate(t.assignee.firstName + " " + t.assignee.familyName)
                || stringPredicate(t.assignee.familyName)
                || stringPredicate(t.taskid);
        };
        this.slicesModelService.rebuildSlices();
    }

    protected updateTaskGrouping(taskGrouping: TaskGrouping): void {
        this.taskGrouping = taskGrouping;
        this.slicesModelService.grouper = this.GROUPERS[taskGrouping];
        this.slicesModelService.rebuildSlices();
    }

    protected toggleHidden(element: Hideable): void {
        element.hidden = !element.hidden;
    }

    protected isHidden(element: Hideable): boolean {
        return element.hidden;
    }

    protected readonly TaskGroupingEnum: typeof TaskGrouping = TaskGrouping;
    protected readonly TASK_GROUPINGS: TaskGrouping[] = Object.values(TaskGrouping);
    protected readonly GROUPERS: { [key in TaskGrouping]: TaskGrouper<GroupingMetadata>; } = {
        [TaskGrouping.NONE]: (ungrouped: Task[]) => {
            return [{ metadata: null, group: ungrouped }]
        },
        [TaskGrouping.BY_ASSIGNEE]: (ungrouped: Task[]) => {
            return this.modelService.assignees.map((a: Assignee) => {
                return {
                    metadata: a,
                    group: ungrouped.filter((t: Task) => {
                        return t.assignee.firstName == a.firstName
                            && t.assignee.familyName == a.familyName
                    })
                };
            });
        },
    };

}
