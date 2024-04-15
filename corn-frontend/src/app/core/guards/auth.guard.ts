import { CanActivateFn, Router } from '@angular/router';
import { inject } from "@angular/core";
import { KeycloakService } from "keycloak-angular";
import { RouterPaths } from "@core/enum/RouterPaths";
import { StorageService } from "@core/services/storage.service";

export const authGuard: CanActivateFn = (route, state) => {
    const keycloakService: KeycloakService = inject(KeycloakService);
    const routerService: Router = inject(Router);
    const storage: StorageService = inject(StorageService);

    if (keycloakService.isLoggedIn()) {
        return true;
    }

    storage.deleteProjectId();

    return routerService.createUrlTree([RouterPaths.HOME_DIRECT_PATH]);
};
