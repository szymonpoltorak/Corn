package keycloakinitializer;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.IdentityProviderRepresentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ExternalConfig {

    private static final String KCCFG_OVERRIDE_EXISTING = System.getenv("KCCFG_OVERRIDE_EXISTING");

    private static final String KCCFG_CREATE_PLACEHOLDER_USERS = System.getenv("KCCFG_CREATE_PLACEHOLDER_USERS");

    public static final String KCCFG_LOGIN_THEME_NAME = System.getenv("KCCFG_LOGIN_THEME_NAME");

    private static final String KC_SERVER_URL = System.getenv("KC_SERVER_URL");

    private static final String GITHUB_CLIENT_ID = System.getenv("GITHUB_CLIENT_ID");
    private static final String GITHUB_CLIENT_SECRET = System.getenv("GITHUB_CLIENT_SECRET");

    private static final String GOOGLE_CLIENT_ID = System.getenv("GOOGLE_CLIENT_ID");
    private static final String GOOGLE_CLIENT_SECRET = System.getenv("GOOGLE_CLIENT_SECRET");

    private ExternalConfig() {
    }

    static Keycloak getAdmin() {
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

    static boolean shouldOverrideExistingConfiguration() {
        return "true".equalsIgnoreCase(KCCFG_OVERRIDE_EXISTING);
    }

    public static boolean shouldCreatePlaceholderUsers() {
        return "true".equalsIgnoreCase(KCCFG_CREATE_PLACEHOLDER_USERS);
    }

    public static List<IdentityProviderRepresentation> getIdentityProviders() {
        List<IdentityProviderRepresentation> providers = new ArrayList<>(3);

        if(isIdentityProviderConfigured(GITHUB_CLIENT_ID, GITHUB_CLIENT_SECRET)) {
            providers.add(newInstance("github", GITHUB_CLIENT_ID, GITHUB_CLIENT_SECRET));
        }
        if(isIdentityProviderConfigured(GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET)) {
            providers.add(newInstance("google", GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET));
        }
        return Collections.unmodifiableList(providers);
    }

    private static IdentityProviderRepresentation newInstance(String provider, String clientId, String clientSecret) {
        IdentityProviderRepresentation idp = new IdentityProviderRepresentation();

        idp.setAlias(provider);
        idp.setEnabled(true);
        idp.setProviderId(provider);
        idp.setConfig(Map.of(
                "clientId", clientId,
                "clientSecret", clientSecret
        ));
        return idp;
    }

    private static boolean isIdentityProviderConfigured(String clientId, String clientSecret) {
        return clientId != null && !clientId.isBlank() && clientSecret != null && !clientSecret.isBlank();
    }

}
