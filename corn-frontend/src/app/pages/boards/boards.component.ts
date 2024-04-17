import { Component, Input, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule, Location } from '@angular/common';
import { SidebarButton } from './sidebar-button/sidebar-button.component';
import { MatToolbar, MatToolbarRow } from '@angular/material/toolbar';
import { MatTab, MatTabGroup } from '@angular/material/tabs';
import { FeatureComponent } from '@pages/home/feature/feature.component';
import { UserinfoComponent } from '@pages/boards/userinfo/userinfo.component';
import { MatMenuModule, MatMenuTrigger } from '@angular/material/menu';
import { RouterPaths } from '@core/enum/RouterPaths';
import { BoardsPaths } from '@core/enum/BoardsPaths';
import { ToolbarComponent } from "@shared/toolbar/toolbar.component";
import { StorageService } from "@core/services/storage.service";
import { StorageKey } from "@core/enum/storage-key.enum";

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
        SidebarButton,
        MatToolbar,
        MatToolbarRow,
        MatTabGroup,
        MatTab,
        FeatureComponent,
        UserinfoComponent,
        MatMenuTrigger,
        MatMenuModule,
        CommonModule,
        ToolbarComponent,
    ],
    templateUrl: './boards.component.html',
})
export class BoardsComponent implements OnInit {

    @Input() sidebarShown: boolean = false;
    @Input() projectName: string = 'Your Project';

    selected: string = '';

    constructor(protected readonly router: Router,
                protected readonly location: Location,
                private readonly storage: StorageService) {
    }

    async ngOnInit(): Promise<void> {
        this.projectName = this.storage.getValueFromStorage(StorageKey.PROJECT_NAME);

        this.selected = this.location.path().split('/').pop() || '';

        this.router.events.subscribe((val) => {
            if (val instanceof NavigationEnd) {
                this.selected = val.url.split('/').pop() || '';
            }
        });
    }

    navigateToBacklog(): void {
        this.router.navigate([`/${ RouterPaths.BOARDS_PATH }/${ BoardsPaths.BACKLOG }`]);
    }

    navigateToTimeline(): void {
        this.router.navigate([`/${ RouterPaths.BOARDS_PATH }/${ BoardsPaths.TIMELINE }`]);
    }

    navigateToBoard(): void {
        this.router.navigate([`/${ RouterPaths.BOARDS_PATH }/${ BoardsPaths.BOARD }`]);
    }
}
