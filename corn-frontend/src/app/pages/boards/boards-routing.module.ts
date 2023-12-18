import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";
import { BoardsComponent } from "./boards.component";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        component: BoardsComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BoardsRoutingModule {
}
