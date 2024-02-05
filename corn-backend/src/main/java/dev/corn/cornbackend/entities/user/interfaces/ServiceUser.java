package dev.corn.cornbackend.entities.user.interfaces;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Interface for service user
 */
public interface ServiceUser {
    /**
     * Get the user's full name
     *
     * @return the user's full name
     */
    String getFullName();

    /**
     * Get the user's authorities
     *
     * @return the user's authorities
     */
    Collection<? extends GrantedAuthority> getAuthorities();
}
