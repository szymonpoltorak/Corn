import { Component, EventEmitter, OnInit, QueryList, ViewChild, ViewChildren, ViewEncapsulation } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatDialog } from "@angular/material/dialog";
import { BacklogFormComponent } from "@pages/boards/backlog/backlog-form/backlog-form.component";
import { take } from "rxjs";
import { BacklogItemService } from "@core/services/boards/backlog/backlog-item/backlog-item.service";
import { Sprint } from "@interfaces/boards/backlog/sprint";
import { BacklogItemTableComponent } from "@pages/boards/backlog/backlog-item-table/backlog-item-table.component";
import {
    MatAccordion,
    MatExpansionPanel,
    MatExpansionPanelDescription,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
} from "@angular/material/expansion";
import { SprintService } from "@core/services/boards/backlog/sprint/sprint.service";
import { DatePipe, NgForOf } from "@angular/common";
import { MatMenu, MatMenuItem, MatMenuModule, MatMenuTrigger } from "@angular/material/menu";
import { CdkContextMenuTrigger, CdkMenu, CdkMenuItem } from "@angular/cdk/menu";
import { MatIcon } from "@angular/material/icon";
import { MatRipple } from "@angular/material/core";

@Component({
    selector: 'app-backlog',
    standalone: true,
    imports: [
        MatButton,
        MatAccordion,
        MatExpansionPanel,
        MatExpansionPanelTitle,
        MatExpansionPanelDescription,
        MatExpansionPanelHeader,
        NgForOf,
        BacklogItemTableComponent,
        DatePipe,
        MatMenuModule,
        CdkContextMenuTrigger,
        CdkMenu,
        CdkMenuItem,
        MatRipple,
    ],
    templateUrl: './backlog.component.html',
    styleUrl: './backlog.component.scss',
    encapsulation: ViewEncapsulation.None
})
export class BacklogComponent implements OnInit {

    @ViewChildren(BacklogItemTableComponent) backlogItemTableComponents!: QueryList<BacklogItemTableComponent>;
    sprintChangedEmitter: EventEmitter<number> = new EventEmitter<number>();
    sprints: Sprint[] = [];
    sprintIds: string[] = [];

    constructor(private dialog: MatDialog,
                private backlogItemService: BacklogItemService,
                private sprintService: SprintService) {
    }

    ngOnInit(): void {
        this.sprintService
            .getCurrentAndFutureSprints()
            .pipe(take(1))
            .subscribe((sprints) => {
                this.sprints = sprints;
                this.sprintIds = sprints.map(sprint => sprint.sprintId.toString());
                this.sprintIds.push('-1');
            })
    }

    showItemForm(): void {
        const dialogRef = this.dialog.open(BacklogFormComponent, {
            enterAnimationDuration: '300ms',
            exitAnimationDuration: '100ms',
        });

        dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
            if (!result) {
                return;
            }
            this.backlogItemService.createNewBacklogItem(
                result.title,
                result.description,
                result.assignee.userId,
                result.sprintId,
                result.type
            ).pipe(take(1)).subscribe((newItem) => {
                const table: BacklogItemTableComponent | undefined = this.findBacklogItemTableById(newItem.sprintId.toString());

                if (table) {
                    table.fetchBacklogItems();
                }
            })
        })
    }

    sprintChanged(sprintId: number): void {
        this.sprintChangedEmitter.emit(sprintId);
    }

    findBacklogItemTableById(id: string): BacklogItemTableComponent | undefined {
        return this.backlogItemTableComponents.find(table => table.sprintId.toString() === id);
    }

    deleteSprint(sprintId: number): void {
        this.sprintService
            .deleteSprint(sprintId)
            .pipe(take(1))
            .subscribe(() => {
                this.sprints = this.sprints.filter(sprint => sprint.sprintId !== sprintId);
            });
    }

    editSprint(sprint: Sprint): void {
        // TODO Add editing sprint data
    }
}
