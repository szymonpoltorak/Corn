import { DataSource } from '@angular/cdk/collections';
import { Component } from '@angular/core';
import {
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
    MatTable
} from "@angular/material/table";
import { BacklogItem } from '@core/interfaces/boards/backlog.item';
import {Observable, ReplaySubject} from "rxjs";
import {NgIcon, provideIcons} from "@ng-icons/core";
import { matTurnedInNot} from "@ng-icons/material-icons/baseline";
import {aspectsInProgress} from "@ng-icons/ux-aspects";
import {matDoneOutline} from "@ng-icons/material-icons/outline";

@Component({
    selector: 'app-backlog',
    standalone: true,
    imports: [
        MatTable,
        MatColumnDef,
        MatHeaderCell,
        MatCell,
        MatCellDef,
        MatHeaderCellDef,
        MatHeaderRow,
        MatHeaderRowDef,
        MatRow,
        MatRowDef,
        NgIcon
    ],
    templateUrl: './backlog.component.html',
    styleUrl: './backlog.component.scss',
    providers: [provideIcons({matDoneOutline, aspectsInProgress, matTurnedInNot})]
})
export class BacklogComponent {


    //only for example purposes
    dataToDisplay: BacklogItem[] = [
        {title: 'Create toolbar', 'description': 'Create a toolbar for user', status: 0},
        {title: 'Keycloak does not work', 'description': 'blabla', status: 0},
        {title: 'Add logout to app', 'description': 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', status: 0},
        {title: 'Improve dockerfile', 'description': 'Papope', status: 1},
        {title: 'Fix content policy', 'description': 'AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA', status: 1},
        {title: 'Create folder structure', 'description': 'idk', status: 1},
        {title: 'Fix equals', 'description': 'example description', status: 2},
        {title: 'Idk do something', 'description': 'hello', status: 2},
        {title: 'Hello World', 'description': 'Hello World!\\n', status: 2}
    ];

    dataSource = new ExampleDataSource(this.dataToDisplay);
    displayedColumns = ['title', 'description','status'];

}

//only for example purposes
class ExampleDataSource extends DataSource<BacklogItem> {
    private _dataStream = new ReplaySubject<BacklogItem[]>();

    constructor(private initialData: BacklogItem[]) {
        super();
        this.setData(initialData);
    }

    connect() : Observable<BacklogItem[]> {
        return this._dataStream;
    }

    disconnect() {};

    setData(data: BacklogItem[]) {
        this._dataStream.next(data);
    }

}
