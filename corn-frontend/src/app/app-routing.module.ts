import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        redirectTo: RouterPath.HOME,
        pathMatch: 'full'
    },
    {
        path: RouterPath.HOME,
        loadChildren: () => import('./pages/home/home.module')
            .then(m => m.HomeModule)
    },
    {
        path: RouterPath.BOARDS,
        loadChildren: () => import('./pages/boards/boards.module')
            .then(m => m.BoardsModule)
    },
    {
        path: RouterPath.ERROR,
        redirectTo: RouterPath.HOME_DIRECT,
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
