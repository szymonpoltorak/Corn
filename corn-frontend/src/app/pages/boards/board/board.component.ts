import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { SlicesModelService } from '@core/services/boards/board/slice/slices_model.service';
import { SliceService } from '@core/services/boards/board/slice/slice.service';
import { SliceComponent } from './slice/slice.component';
import { BoardModelService } from '@core/services/boards/board/model.service';
import { MatIconModule } from '@angular/material/icon';
import { Task } from '@core/interfaces/boards/board/task.interface';
import { Assignee } from '@core/interfaces/boards/board/assignee.interface';
import { TaskGrouping } from '@core/enum/boards/board/TaskGrouping';
import { ColumnSetLayout } from './layout/column_set_layout.component';
import { GroupingMetadata } from '@core/types/board/boards/GroupingMetadata';
import { TaskGrouper } from '@core/types/board/boards/TaskGrouper';
import { TaskChangedGroupEvent } from '@core/interfaces/boards/board/task_changed_group_event.interface';
import { TaskChangedColumnEvent } from '@core/interfaces/boards/board/task_changed_column_event.interface';
import { Hideable } from '@core/interfaces/boards/board/hideable.interface';
import { SprintApi } from '@core/services/api/v1/sprint/sprint-api.service';
import { StorageService } from '@core/services/storage.service';
import { SprintResponse } from '@core/services/api/v1/sprint/data/sprint-response.interface';
import { ProjectMemberApi } from '@core/services/api/v1/project/member/project-member-api.service';
import { BacklogItemApi } from '@core/services/api/v1/backlog/item/backlog-item-api.service';
import { BacklogItemStatus } from '@core/enum/BacklogItemStatus';
import { StorageKey } from '@core/enum/storage-key.enum';

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

    protected currentSprint: SprintResponse | null | undefined = undefined;
    protected filterString: string = "";
    protected taskGrouping: TaskGrouping = TaskGrouping.NONE;

    constructor(
        protected readonly sprintApi: SprintApi,
        protected readonly projectMemberApi: ProjectMemberApi,
        protected readonly backlogItemApi: BacklogItemApi,
        protected readonly storage: StorageService,
        protected readonly modelService: BoardModelService,
        protected readonly slicesModelService: SlicesModelService<GroupingMetadata>,
    ) {
    }

    ngOnInit(): void {
        this.modelService.modelChangeHandler = () => {
            this.slicesModelService.rebuildSlices();
        }
        this.slicesModelService.groupChangedHandler = this.groupChangedHandler.bind(this);
        this.slicesModelService.columnChangedHandler = this.columnChangedHandler.bind(this);
        this.sprintApi.getCurrentAndFutureSprints(this.storage.getValueFromStorage(StorageKey.PROJECT_ID)).subscribe(list => {
            this.setSprint(list.length > 0 ? list[0] : null);
        });
    }

    protected assigneeChangedHandler(event: TaskChangedGroupEvent<Assignee>): void {
        this.modelService.setAssigneeForTask(event.task, event.destinationGroupMetadata);
        this.backlogItemApi.partialUpdate(event.task.associatedBacklogItemId, {
            projectMemberId: event.task.assignee.associatedProjectMemberId,
        }).subscribe((_) => { });
    }

    protected groupChangedHandler(event: TaskChangedGroupEvent<GroupingMetadata>): void {
        if (this.taskGrouping == TaskGrouping.BY_ASSIGNEE &&
            event.sourceGroupMetadata != event.destinationGroupMetadata &&
            event.destinationGroupMetadata != null &&
            event.destinationGroupMetadata != undefined
        ) {
            this.assigneeChangedHandler(event as TaskChangedGroupEvent<Assignee>);
            this.modelService.setAssigneeForTask(event.task, event.destinationGroupMetadata);
        }
    }

    private columnChangedHandler(event: TaskChangedColumnEvent): void {
        this.modelService.moveTaskToArray(event.task,
            event.sourceColumn, event.sourceColumnIndex,
            event.destinationColumn, event.destinationColumnIndex
        );
        const status = (() => {
            if (event.destinationColumn === this.modelService.todo) {
                return BacklogItemStatus.TODO;
            } else if (event.destinationColumn === this.modelService.inprogress) {
                return BacklogItemStatus.IN_PROGRESS;
            } else if (event.destinationColumn === this.modelService.done) {
                return BacklogItemStatus.DONE;
            }
            throw new Error("Unknown column");
        })();
        setTimeout(() => {
            // this prevents race condition
            this.backlogItemApi.partialUpdate(event.task.associatedBacklogItemId, {
                itemStatus: status,
            }).subscribe((_) => { });
        }, 500);
    }

    protected setSprint(sprint: SprintResponse | null | undefined): void {
        if (sprint === null || sprint === undefined) {
            return;
        }
        this.currentSprint = undefined;
        this.projectMemberApi.getAllProjectMembers(this.storage.getValueFromStorage(StorageKey.PROJECT_ID)).subscribe(members => {
            this.modelService.assignees = members.map(member => {
                return {
                    associatedUserId: member.user.userId,
                    associatedUsername: member.user.username,
                    associatedProjectMemberId: member.projectMemberId,
                    firstName: member.user.name,
                    familyName: member.user.surname,
                    avatarUrl: "/assets/assignee-avatars/alice.png",
                };
            });
            const userIdToAssigneesMap = this.modelService.assignees.reduce((acc: any, next) => {
                acc[next.associatedUsername] = next;
                return acc;
            }, {})
            this.backlogItemApi.getAllBySprintId(sprint.sprintId).subscribe(items => {
                this.modelService.todo = [];
                this.modelService.inprogress = [];
                this.modelService.done = [];
                items.forEach(item => {
                    const task: Task = {
                        associatedBacklogItemId: item.backlogItemId,
                        taskTag: item.itemType + "-" + item.backlogItemId,
                        content: item.title,
                        assignee: userIdToAssigneesMap[item.assignee.username],
                    };
                    if (item.status == BacklogItemStatus.TODO) {
                        this.modelService.todo.push(task)
                    } else if (item.status == BacklogItemStatus.IN_PROGRESS) {
                        this.modelService.inprogress.push(task)
                    } else if (item.status == BacklogItemStatus.DONE) {
                        this.modelService.done.push(task)
                    }
                })
                this.currentSprint = sprint;
                this.updateFilterString(this.filterString);
                this.updateTaskGrouping(this.taskGrouping);
            });
        });
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
                || stringPredicate(t.associatedBacklogItemId + "")
                || stringPredicate(t.taskTag);
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
