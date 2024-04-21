import { Routes } from '@angular/router';
import { BoardsPaths } from '@core/enum/BoardsPaths';
import { RouterPaths } from "@core/enum/RouterPaths";
import { authGuard } from '@core/guards/auth.guard';
import { projectGuard } from '@core/guards/project.guard';

export const routes: Routes = [
    {
        path: RouterPaths.HOME_PATH,
        loadComponent: () => import("@pages/home/home.component")
            .then(c => c.HomeComponent)
    },
    {
        path: RouterPaths.PROJECT_LIST_PATH,
        loadComponent: () => import("@pages/project-list/project-list.component")
            .then(c => c.ProjectListComponent)
    },
    {
        path: RouterPaths.BOARDS_PATH,
        loadComponent: () => import("@pages/boards/boards.component")
            .then(c => c.BoardsComponent),
        canActivate: [authGuard, projectGuard],
        children: [
            {
                path: BoardsPaths.BACKLOG,
                loadComponent: () => import("@pages/boards/backlog/backlog.component")
                    .then(c => c.BacklogComponent)
            },
            {
                path: BoardsPaths.TIMELINE,
                loadComponent: () => import("@pages/boards/timeline/timeline.component")
                    .then(c => c.TimelineComponent)
            },
            {
                path: BoardsPaths.BOARD,
                loadComponent: () => import("@pages/boards/board/board.component")
                    .then(c => c.BoardComponent)
            },
            {
                path: BoardsPaths.REPORTS,
                loadComponent: () => import("@pages/boards/reports/reports.component")
                    .then(c => c.ReportsComponent)
            },
            {
                path: RouterPaths.SETTINGS_PATH,
                loadComponent: () => import("@pages/boards/project-settings/project-settings.component")
                    .then(c => c.ProjectSettingsComponent)
            }
        ],
    },
    {
        path: RouterPaths.CURRENT_PATH,
        redirectTo: RouterPaths.HOME_PATH,
        pathMatch: "full"
    },
    {
        path: RouterPaths.UNKNOWN_PATH,
        redirectTo: RouterPaths.HOME_PATH,
        pathMatch: "full"
    }
];
