package keycloakinitializer.realm.corn;

import keycloakinitializer.ExternalConfig;
import org.keycloak.representations.idm.RealmRepresentation;

import java.util.List;

public final class CornRealm extends RealmRepresentation {
    private static final String REALM_NAME = "Corn";

    public CornRealm() {
        setRealm(REALM_NAME);
        setEnabled(true);
        setRegistrationAllowed(true);
        setClients(List.of(new CornClient()));
        setIdentityProviders(ExternalConfig.getIdentityProviders());
        setRevokeRefreshToken(true);
        setRememberMe(true);
        setBruteForceProtected(true);

        if(ExternalConfig.KCCFG_LOGIN_THEME_NAME != null) {
            setLoginTheme(ExternalConfig.KCCFG_LOGIN_THEME_NAME);
        }
    }

}
