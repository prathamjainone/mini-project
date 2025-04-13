package io.github.prathamjainone.productivity.users.service;

import io.github.prathamjainone.productivity.users.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory service implementation for managing user operations.
 * This class handles business logic related to Users and stores data in memory.
 * It implements the {@link UserService} interface.
 *
 * <p>
 * This is typically used in testing or scenarios where persistent storage is not required.
 * </p>
 */
@Service
public class InMemoryUserService implements UserService {

    private final List<User> users;

    /**
     * Constructs a new {@link InMemoryUserService} with an empty user list.
     */
    public InMemoryUserService() {
        this.users = new ArrayList<>();
    }

    /**
     * Creates a new user in memory.
     *
     * @param user the user to create
     * @return the created {@link User}
     */
    @Override
    public User createUser(User user) {
        users.add(user);
        return user;
    }

    /**
     * Retrieves all users from memory.
     *
     * @return a list of all users
     */
    @Override
    public List<User> getAllUsers() {
        return users;
    }

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id the unique identifier of the user
     * @return the {@link User} with the given ID, or {@code null} if no user is found
     */
    @Override
    public User getUserById(long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user
     * @return the {@link User} with the given username, or {@code null} if no user is found
     */
    @Override
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Updates the password of an existing user.
     *
     * @param id the unique identifier of the user
     * @param newPassword the new password for the user
     * @return the updated {@link User} entity, or {@code null} if the user is not found
     */
    @Override
    public User updateUserPassword(long id, String newPassword) {
        User user = getUserById(id);
        if (user != null) {
            user.setPassword(newPassword);
        }
        return user;
    }

    /**
     * Updates the username of an existing user.
     *
     * @param id the unique identifier of the user
     * @param newUsername the new username for the user
     * @return the updated {@link User} entity, or {@code null} if the user is not found
     */
    @Override
    public User updateUsername(long id, String newUsername) {
        User user = getUserById(id);
        if (user != null) {
            user.setUsername(newUsername);
        }
        return user;
    }

    /**
     * Updates the email address of an existing user.
     *
     * @param id the unique identifier of the user
     * @param newEmail the new email address for the user
     * @return the updated {@link User} entity, or {@code null} if the user is not found
     */
    @Override
    public User updateEmail(long id, String newEmail) {
        User user = getUserById(id);
        if (user != null) {
            user.setEmail(newEmail);
        }
        return user;
    }

    /**
     * Updates the full name of an existing user.
     *
     * @param id the unique identifier of the user
     * @param newFullName the new full name for the user
     * @return the updated {@link User} entity, or {@code null} if the user is not found
     */
    @Override
    public User updateFullName(long id, String newFullName) {
        User user = getUserById(id);
        if (user != null) {
            user.setFullName(newFullName);
        }
        return user;
    }

    /**
     * Deletes a user from memory.
     *
     * @param id the unique identifier of the user to delete
     * @return {@code true} if the user was successfully deleted, {@code false} otherwise
     */
    @Override
    public boolean deleteUser(long id) {
        User user = getUserById(id);
        if (user != null) {
            users.remove(user);
            return true;
        }
        return false;
    }
}
