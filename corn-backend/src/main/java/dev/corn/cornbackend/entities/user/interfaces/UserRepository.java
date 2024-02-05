package dev.corn.cornbackend.entities.user.interfaces;

import dev.corn.cornbackend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find user by username.
     *
     * @param username username
     * @return user
     */
    Optional<User> findByUsername(String username);
}
