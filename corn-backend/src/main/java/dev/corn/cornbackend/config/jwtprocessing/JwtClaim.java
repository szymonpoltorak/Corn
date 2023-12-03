package dev.corn.cornbackend.config.jwtprocessing;

import org.springframework.security.oauth2.jwt.Jwt;

public enum JwtClaim {

    USERNAME("preferred_username"),
    NAME("given_name"),
    SURNAME("family_name");

    private final String claimName;

    JwtClaim(String claimName) {
        this.claimName = claimName;
    }

    public String getClaim(Jwt jwt) {
        return jwt.getClaim(claimName);
    }

}
