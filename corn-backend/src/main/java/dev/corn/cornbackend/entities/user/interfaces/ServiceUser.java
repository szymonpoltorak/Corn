package dev.corn.cornbackend.entities.user.interfaces;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface ServiceUser {
    String getFullName();

    Collection<? extends GrantedAuthority> getAuthorities();

}
