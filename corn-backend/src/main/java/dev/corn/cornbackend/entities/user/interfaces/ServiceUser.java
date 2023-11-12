package dev.corn.cornbackend.entities.user.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface ServiceUser extends UserDetails {
    String getFullName();
}
