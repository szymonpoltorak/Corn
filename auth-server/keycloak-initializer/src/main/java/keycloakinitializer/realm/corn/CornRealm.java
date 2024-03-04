package keycloakinitializer.realm.corn;

import keycloakinitializer.ExternalConfig;
import org.keycloak.representations.idm.RealmRepresentation;

import java.util.List;

public final class CornRealm extends RealmRepresentation {
    private static final String REALM_NAME = "Corn";
    private static final String DEFAULT_SIGNATURE_ALGORITHM = "ES512";

    public CornRealm() {
        setRealm(REALM_NAME);
        setEnabled(true);
        setRegistrationAllowed(true);
        setClients(List.of(new CornClient()));
        setIdentityProviders(ExternalConfig.getIdentityProviders());
        setRevokeRefreshToken(true);
        setRememberMe(true);
        setBruteForceProtected(true);
        setDefaultSignatureAlgorithm(DEFAULT_SIGNATURE_ALGORITHM);

        if(ExternalConfig.KCCFG_LOGIN_THEME_NAME != null) {
            setLoginTheme(ExternalConfig.KCCFG_LOGIN_THEME_NAME);
        }
    }

}
