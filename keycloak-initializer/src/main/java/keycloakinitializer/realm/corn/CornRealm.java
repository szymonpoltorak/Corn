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
        setUsers(new Users());
        setIdentityProviders(ExternalConfig.getIdentityProviders());
    }

    public enum Role {
        ADMIN, USER;
    }

}
