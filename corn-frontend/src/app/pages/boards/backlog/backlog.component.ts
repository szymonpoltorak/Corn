import { Component, OnInit, QueryList, ViewChildren, ViewEncapsulation } from '@angular/core';
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
import { NgForOf } from "@angular/common";

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
        BacklogItemTableComponent
    ],
    templateUrl: './backlog.component.html',
    styleUrl: './backlog.component.scss',
    encapsulation: ViewEncapsulation.None
})
export class BacklogComponent implements OnInit {

    constructor(private dialog: MatDialog,
                private backlogItemService: BacklogItemService,
                private sprintService: SprintService) {
    }

    sprints: Sprint[] = [];
    sprintIds: string[] = [];

    ngOnInit(): void {
        //TODO get real projectId from somewhere
        this.sprintService.getCurrentAndFutureSprints().pipe(take(1)).subscribe((sprints) => {
            this.sprints = sprints;
            this.sprintIds = sprints.map(sprint => sprint.sprintId.toString());
            this.sprintIds.push('-1')
        })
    }

    @ViewChildren(BacklogItemTableComponent) backlogItemTableComponents!: QueryList<BacklogItemTableComponent>;

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
                if(table) {
                    table.fetchBacklogItems();
                }
            })
        })
    }

    findBacklogItemTableById(id: string): BacklogItemTableComponent | undefined {
        return this.backlogItemTableComponents.find(table => table.sprintId.toString() === id);
    }
}
