package keycloakinitializer;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.IdentityProviderRepresentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ExternalConfig {

    public static final String KCCFG_OVERRIDE_EXISTING = System.getenv("KCCFG_OVERRIDE_EXISTING");

    public static final String KCCFG_LOGIN_THEME_NAME = System.getenv("KCCFG_LOGIN_THEME_NAME");

    public static final String KC_SERVER_URL = System.getenv("KC_SERVER_URL");

    public static final String GITHUB_CLIENT_ID = System.getenv("GITHUB_CLIENT_ID");
    public static final String GITHUB_CLIENT_SECRET = System.getenv("GITHUB_CLIENT_SECRET");

    public static final String GOOGLE_CLIENT_ID = System.getenv("GOOGLE_CLIENT_ID");
    public static final String GOOGLE_CLIENT_SECRET = System.getenv("GOOGLE_CLIENT_SECRET");

    //

    public static Keycloak getAdmin() {
        if(KC_SERVER_URL == null) {
            throw new IllegalStateException("env KC_SERVER_URL not found");
        }
        return KeycloakBuilder.builder()
                .serverUrl(KC_SERVER_URL)
                .realm("master")
                .username("admin")
                .password("admin")
                .clientId("admin-cli")
                .build();
    }

    public static boolean shouldOverrideExistingConfiguration() {
        return KCCFG_OVERRIDE_EXISTING != null && KCCFG_OVERRIDE_EXISTING.equalsIgnoreCase("true");
    }

    public static List<IdentityProviderRepresentation> getIdentityProviders() {
        ArrayList<IdentityProviderRepresentation> providers = new ArrayList<>();
        if(GITHUB_CLIENT_ID != null && !GITHUB_CLIENT_ID.isBlank() && GITHUB_CLIENT_SECRET != null && !GITHUB_CLIENT_SECRET.isBlank()) {
            providers.add(new IdentityProviderRepresentation() {{
                setAlias("github");
                setEnabled(true);
                setProviderId("github");
                setConfig(Map.of(
                        "clientId", GITHUB_CLIENT_ID,
                        "clientSecret", GITHUB_CLIENT_SECRET
                ));
                // V czy wymusić aktualizację danych profilu po pierwszym zalogowaniu przez oauth2
                //setUpdateProfileFirstLoginMode("on");
                // V ??
                //setFirstBrokerLoginFlowAlias("first broker login");
                // V napis na ekranie logowania
                //setDisplayName("Zaloguj sie przez ~GITHUB~");
            }});
        }
        if(GOOGLE_CLIENT_ID != null && !GOOGLE_CLIENT_ID.isBlank() && GOOGLE_CLIENT_SECRET != null && !GOOGLE_CLIENT_SECRET.isBlank()) {
            providers.add(new IdentityProviderRepresentation() {{
                setAlias("google");
                setEnabled(true);
                setProviderId("google");
                setConfig(Map.of(
                        "clientId", GOOGLE_CLIENT_ID,
                        "clientSecret", GOOGLE_CLIENT_SECRET
                ));
            }});
        }
        return Collections.unmodifiableList(providers);
    }

}
