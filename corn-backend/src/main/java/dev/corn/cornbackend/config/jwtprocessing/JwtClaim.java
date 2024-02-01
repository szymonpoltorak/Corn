package dev.corn.cornbackend.config.jwtprocessing;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;

@RequiredArgsConstructor
public enum JwtClaim {

    USERNAME("preferred_username"),
    NAME("given_name"),
    SURNAME("family_name");

    private final String claimName;

    final String getClaim(Jwt jwt) {
        return jwt.getClaim(claimName);
    }

}
