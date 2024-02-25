import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { SlicesModelService, TaskChangedColumnEvent, TaskChangedGroupEvent } from './slice/slices_model.service';
import { SliceService } from './slice/slice.service';
import { SliceComponent } from './slice/slice.component';
import { Assignee, SAMPLE_ASSIGNEES, SAMPLE_TASKS, Task } from './model/model';
import { ModelService } from './model/model.service';
import { ColumnSetLayout } from './layout.component';
import { MatIconModule } from '@angular/material/icon';

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
        ModelService,
        SlicesModelService,
        SliceService,
    ],
    templateUrl: './board.component.html',
})
export class BoardComponent implements OnInit {

    protected filterString = "";
    protected taskGrouping = TaskGrouping.BY_ASSIGNEE;

    constructor(
        protected readonly modelService: ModelService,
        protected readonly slicesModelService: SlicesModelService,
    ) { }

    ngOnInit() {
        this.modelService.modelChangeHandler = () => {
            this.slicesModelService.rebuildSlices();
        }
        this.modelService.assignees = Object.values(SAMPLE_ASSIGNEES);
        this.modelService.todo = SAMPLE_TASKS.TODO;
        this.modelService.inprogress = SAMPLE_TASKS.INPROGRESS;
        this.modelService.done = SAMPLE_TASKS.DONE;
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

    protected updateFilterString(filterString: string) {
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

    protected updateTaskGrouping(taskGrouping: TaskGrouping) {
        this.taskGrouping = taskGrouping;
        this.slicesModelService.grouper = this.GROUPERS[taskGrouping];
        this.slicesModelService.rebuildSlices();
    }

    protected toggleHidden(element: any) {
        element.hidden = !element.hidden;
    }

    protected isHidden(element: any) {
        return element.hidden;
    }

    protected readonly TaskGroupingEnum = TaskGrouping;
    protected readonly TASK_GROUPINGS = Object.values(TaskGrouping);
    protected readonly GROUPERS = {
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

enum TaskGrouping {
    NONE = "None",
    BY_ASSIGNEE = "By assignee",
}
