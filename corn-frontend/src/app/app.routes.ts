import { Routes } from '@angular/router';
import { RouterPaths } from "@core/enum/RouterPaths";

export const routes: Routes = [
    {
        path: RouterPaths.HOME_PATH,
        loadComponent: () => import("@pages/home/home.component")
            .then(c => c.HomeComponent)
    },
    {
        path: RouterPaths.CURRENT_PATH,
        redirectTo: RouterPaths.HOME_PATH,
        pathMatch: "full"
    }
];
