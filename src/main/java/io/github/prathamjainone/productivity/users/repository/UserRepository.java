package io.github.prathamjainone.productivity.users.repository;

import io.github.prathamjainone.productivity.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * Provides methods to perform CRUD operations on the "users" table in the database.
 * Extends {@link JpaRepository} to leverage built-in JPA functionality.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user
     * @return an {@link Optional} containing the user if found, otherwise empty
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user
     * @return an {@link Optional} containing the user if found, otherwise empty
     */
    Optional<User> findByEmail(String email);
}
