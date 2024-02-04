package keycloakinitializer.realm.corn;

import keycloakinitializer.ExternalConfig;
import org.keycloak.representations.idm.RealmRepresentation;

import java.util.List;

public class CornRealm extends RealmRepresentation {

    public CornRealm() {
        setRealm("Corn");
        setEnabled(true);
        setRegistrationAllowed(true);
        setClients(List.of(new CornClient()));
        setIdentityProviders(ExternalConfig.getIdentityProviders());
        if(ExternalConfig.KCCFG_LOGIN_THEME_NAME != null) {
            setLoginTheme(ExternalConfig.KCCFG_LOGIN_THEME_NAME);
        }
    }

    public enum Role {
        ADMIN, USER;
    }

}
