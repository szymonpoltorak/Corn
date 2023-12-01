package dev.corn.cornbackend.config.jwtprocessing;

import org.springframework.security.oauth2.jwt.Jwt;

public class JwtProcessingUtil {

    private static final String PRINCIPAL_CLAIM_NAME = "preferred_username";
    private static final String NAME = "given_name";
    private static final String SURNAME = "family_name";

    public static String getPrincipalClaimName(Jwt jwt) {
        return jwt.getClaim(PRINCIPAL_CLAIM_NAME);
    }

    public static String getName(Jwt jwt) {
        return jwt.getClaim(NAME);
    }

    public static String getSurname(Jwt jwt) {
        return jwt.getClaim(SURNAME);
    }

}
