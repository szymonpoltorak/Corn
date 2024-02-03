import { Component, Input } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { SidebarButton } from './sidebar-button/sidebar-button.component';
import { Location } from '@angular/common';

@Component({
    selector: 'app-boards',
    standalone: true,
    imports: [
        RouterOutlet,
        MatSidenavModule,
        MatFormFieldModule,
        MatSelectModule,
        MatButtonModule,
        MatIconModule,
        CommonModule,
        SidebarButton
    ],
    templateUrl: './boards.component.html',
})
export class BoardsComponent {

    @Input() sidebarShown: boolean = false;
    @Input() projectName: string = '';

    selected: string = '';

    constructor(
        readonly route: ActivatedRoute,
        readonly router: Router,
        readonly location: Location,
    ) { }

    ngOnInit(): void {
        this.selected = this.location.path().split('/').pop() || '';
        this.router.events.subscribe((val) => {
            if(val instanceof NavigationEnd) {
                this.selected = val.url.split('/').pop() || '';
            }
        });
    }

    navigateToBacklog() {
        this.router.navigate(['/boards/backlog']);
    }

    navigateToTimeline() {
        this.router.navigate(['/boards/timeline']);
    }

    toggleSidebar() {
        this.sidebarShown = !this.sidebarShown;
    }

}
