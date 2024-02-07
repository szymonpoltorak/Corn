import {Component, HostListener} from '@angular/core';
import {ProjectComponent} from "@pages/project-list/project/project.component";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";

@Component({
  selector: 'app-project-list',
  standalone: true,
    imports: [
        ProjectComponent,
        MatGridList,
        MatGridTile
    ],
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.scss'
})
export class ProjectListComponent {

    cols = 5;

    @HostListener('window:resize', ['$event'])
    onResize(event: any) {
        this.adjustCols();
    }

    adjustCols() {
        if(window.innerWidth <= 840) {
            this.cols = 3;
        } else {
            this.cols = 5;
        }
    }

}
