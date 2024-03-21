import { CanActivateFn, Router } from '@angular/router';
import { inject } from "@angular/core";
import { KeycloakService } from "keycloak-angular";
import { RouterPaths } from "@core/enum/router-paths";

export const authGuard: CanActivateFn = (route, state) => {
    const keycloakService: KeycloakService = inject(KeycloakService);
    const routerService: Router = inject(Router);

    if (keycloakService.isLoggedIn()) {
        return true;
    }
    return routerService.createUrlTree([RouterPaths.HOME_DIRECT_PATH]);
};
