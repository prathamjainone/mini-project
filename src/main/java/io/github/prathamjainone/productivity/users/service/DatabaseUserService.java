package io.github.prathamjainone.productivity.users.service;

import io.github.prathamjainone.productivity.users.exceptions.UserNotFoundException;
import io.github.prathamjainone.productivity.users.exceptions.UserValidationException;
import io.github.prathamjainone.productivity.users.model.User;
import io.github.prathamjainone.productivity.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation that uses a database to store and manage user information.
 * This class implements the {@link UserService} interface and uses {@link UserRepository}
 * to perform CRUD operations on user entities.
 * Provides various methods to create, update, retrieve, and delete users.
 */
@Service
public class DatabaseUserService implements UserService {

    private final UserRepository userRepository;

    /**
     * Constructs a {@link DatabaseUserService} with the given {@link UserRepository}.
     *
     * @param userRepository the repository to interact with the database for user operations
     */
    @Autowired
    public DatabaseUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user in the system.
     *
     * @param user the user information to create
     * @return the saved {@link User} entity
     * @throws UserValidationException if the user is invalid (null)
     */
    @Override
    public User createUser(User user) {
        if (user == null) {
            throw new UserValidationException("User must not be null");
        }
        return userRepository.save(user);
    }

    /**
     * Retrieves all users from the system.
     *
     * @return a list of all users
     * @throws UserNotFoundException if no users are found in the system
     */
    @Override
    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw new UserNotFoundException("No users found");
        }
        return userList;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the unique identifier of the user
     * @return the {@link User} with the given ID
     * @throws UserNotFoundException if no user is found with the given ID
     */
    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user
     * @return the {@link User} with the given username
     * @throws UserNotFoundException if no user is found with the given username
     */
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username '" + username + "' not found"));
    }

    /**
     * Updates the password of an existing user.
     *
     * @param id          the unique identifier of the user
     * @param newPassword the new password for the user (must be at least 6 characters)
     * @return the updated {@link User} entity
     * @throws UserValidationException if the new password is invalid (less than 6 characters)
     * @throws UserNotFoundException   if no user is found with the given ID
     */
    @Override
    public User updateUserPassword(long id, String newPassword) {
        if (newPassword == null || newPassword.length() < 6) {
            throw new UserValidationException("Password must be at least 6 characters");
        }
        User user = getUserById(id);
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    /**
     * Updates the username of an existing user.
     *
     * @param id            the unique identifier of the user
     * @param newUsername   the new username (must be between 3 and 20 characters)
     * @return the updated {@link User} entity
     * @throws UserValidationException if the new username is invalid
     * @throws UserNotFoundException   if no user is found with the given ID
     */
    @Override
    public User updateUsername(long id, String newUsername) {
        if (newUsername == null || newUsername.trim().isEmpty() || newUsername.length() < 3 || newUsername.length() > 20) {
            throw new UserValidationException("Username must be between 3 and 20 characters and cannot be empty");
        }
        User user = getUserById(id);
        user.setUsername(newUsername);
        return userRepository.save(user);
    }

    /**
     * Updates the email of an existing user.
     *
     * @param id          the unique identifier of the user
     * @param newEmail    the new email address (must be a valid email format)
     * @return the updated {@link User} entity
     * @throws UserValidationException if the new email is invalid
     * @throws UserNotFoundException   if no user is found with the given ID
     */
    @Override
    public User updateEmail(long id, String newEmail) {
        if (newEmail == null || newEmail.trim().isEmpty() || !newEmail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new UserValidationException("Invalid email format");
        }
        User user = getUserById(id);
        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    /**
     * Updates the full name of an existing user.
     *
     * @param id          the unique identifier of the user
     * @param newFullName the new full name (must be between 2 and 100 characters)
     * @return the updated {@link User} entity
     * @throws UserValidationException if the new full name is invalid
     * @throws UserNotFoundException   if no user is found with the given ID
     */
    @Override
    public User updateFullName(long id, String newFullName) {
        if (newFullName == null || newFullName.trim().isEmpty() || newFullName.length() < 2 || newFullName.length() > 100) {
            throw new UserValidationException("Full name must be between 2 and 100 characters and cannot be empty");
        }
        User user = getUserById(id);
        user.setFullName(newFullName);
        return userRepository.save(user);
    }

    /**
     * Deletes a user from the system.
     *
     * @param id the unique identifier of the user to delete
     * @return true if the user was successfully deleted, false otherwise
     * @throws UserNotFoundException if no user is found with the given ID
     */
    @Override
    public boolean deleteUser(long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }
}
