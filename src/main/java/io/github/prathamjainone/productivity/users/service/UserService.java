package io.github.prathamjainone.productivity.users.service;

import io.github.prathamjainone.productivity.users.exceptions.UserNotFoundException;
import io.github.prathamjainone.productivity.users.exceptions.UserValidationException;
import io.github.prathamjainone.productivity.users.model.User;

import java.util.List;

/**
 * Interface for user management services.
 * Defines operations for creating, reading, updating, and deleting user data.
 * Implementations of this interface should handle the business logic for user operations
 * and interact with persistent storage or in-memory data.
 *
 * <p>
 * The methods throw specific exceptions when an operation cannot be performed, such as:
 * </p>
 *
 * <ul>
 *     <li>{@link UserValidationException} for invalid user data.</li>
 *     <li>{@link UserNotFoundException} for non-existent users.</li>
 * </ul>
 */
public interface UserService {

    /**
     * Creates a new user.
     *
     * @param user the {@link User} object to create
     * @return the created {@link User}
     * @throws UserValidationException if the provided user data is invalid
     */
    User createUser(User user) throws UserValidationException;

    /**
     * Retrieves all users.
     *
     * @return a list of all {@link User} objects
     * @throws UserNotFoundException if no users are found
     */
    List<User> getAllUsers() throws UserNotFoundException;

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id the unique identifier of the user
     * @return the {@link User} with the given ID
     * @throws UserNotFoundException if no user with the given ID exists
     */
    User getUserById(long id) throws UserNotFoundException;

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user
     * @return the {@link User} with the given username
     * @throws UserNotFoundException if no user with the given username exists
     */
    User getUserByUsername(String username) throws UserNotFoundException;

    /**
     * Updates the password of an existing user.
     *
     * @param id the unique identifier of the user
     * @param newPassword the new password for the user
     * @return the updated {@link User} entity
     * @throws UserValidationException if the new password is invalid
     */
    User updateUserPassword(long id, String newPassword) throws UserValidationException;

    /**
     * Updates the username of an existing user.
     *
     * @param id the unique identifier of the user
     * @param newUsername the new username for the user
     * @return the updated {@link User} entity
     * @throws UserValidationException if the new username is invalid
     */
    User updateUsername(long id, String newUsername) throws UserValidationException;

    /**
     * Updates the email address of an existing user.
     *
     * @param id the unique identifier of the user
     * @param newEmail the new email address for the user
     * @return the updated {@link User} entity
     * @throws UserValidationException if the new email is invalid
     */
    User updateEmail(long id, String newEmail) throws UserValidationException;

    /**
     * Updates the full name of an existing user.
     *
     * @param id the unique identifier of the user
     * @param newFullName the new full name for the user
     * @return the updated {@link User} entity
     * @throws UserValidationException if the new full name is invalid
     */
    User updateFullName(long id, String newFullName) throws UserValidationException;

    /**
     * Deletes a user by their unique ID.
     *
     * @param id the unique identifier of the user
     * @return {@code true} if the user was deleted successfully, {@code false} otherwise
     * @throws UserNotFoundException if no user with the given ID exists
     */
    boolean deleteUser(long id) throws UserNotFoundException;
}
