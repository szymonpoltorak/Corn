import { CanActivateFn, Router } from '@angular/router';
import { inject } from "@angular/core";
import { RouterPaths } from "@core/enum/RouterPaths";
import { StorageService } from "@core/services/storage.service";

export const projectGuard: CanActivateFn = (route, state) => {
    const routerService: Router = inject(Router);
    const storage: StorageService = inject(StorageService);

    if (storage.isProjectIdSet()) {
        return true;
    }

    return routerService.createUrlTree([RouterPaths.PROJECT_LIST_DIRECT_PATH]);
};
