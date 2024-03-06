package keycloakinitializer.realm.corn;

import org.keycloak.representations.idm.ClientRepresentation;

import java.util.List;

final class CornClient extends ClientRepresentation {
    private static final String CLIENT_ID = "Corn";
    private static final String OPENID_CONNECT = "openid-connect";

    CornClient() {
        setClientId(CLIENT_ID);
        setEnabled(true);
        setRedirectUris(List.of(
            "http://localhost/*",
            "http://localhost:4200/*",
            "http://localhost:80/*"
            ));
        setDirectAccessGrantsEnabled(true);
        setProtocol(OPENID_CONNECT);
        setPublicClient(true);
        setFullScopeAllowed(true);
    }

}
