package keycloakinitializer.realm.corn;

import org.keycloak.representations.idm.ClientRepresentation;

import java.util.List;

public class CornClient extends ClientRepresentation {

    public CornClient() {
        setClientId("Corn");
        setEnabled(true);
        setRedirectUris(List.of(
            "http://localhost/*",
            "http://localhost:4200/*",
            "http://localhost:80/*",
            "http://corn.zet/*",
            "http://auth.corn.zet/*"
            ));
        setDirectAccessGrantsEnabled(true);
        setPublicClient(true);
    }

}
