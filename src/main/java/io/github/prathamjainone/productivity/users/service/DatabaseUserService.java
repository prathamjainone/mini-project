package io.github.prathamjainone.productivity.users.service;

import io.github.prathamjainone.productivity.users.exceptions.UserNotFoundException;
import io.github.prathamjainone.productivity.users.exceptions.UserValidationException;
import io.github.prathamjainone.productivity.users.model.User;
import io.github.prathamjainone.productivity.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A service that uses a database to store and manage user information.
 * This class implements the UserService interface and uses a UserRepository
 * to perform operations on the database.
 */
@Service
public class DatabaseUserService implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public DatabaseUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        if (user == null) {
            throw new UserValidationException("User must not be null");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw new UserNotFoundException("No users found");
        }
        return userList;
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username '" + username + "' not found"));
    }

    @Override
    public User updateUserPassword(long id, String newPassword) {
        if (newPassword == null || newPassword.length() < 6) {
            throw new UserValidationException("Password must be at least 6 characters");
        }
        User user = getUserById(id);
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    @Override
    public User updateUsername(long id, String newUsername) {
        if (newUsername == null || newUsername.trim().isEmpty() || newUsername.length() < 3 || newUsername.length() > 20) {
            throw new UserValidationException("Username must be between 3 and 20 characters and cannot be empty");
        }
        User user = getUserById(id);
        user.setUsername(newUsername);
        return userRepository.save(user);
    }

    @Override
    public User updateEmail(long id, String newEmail) {
        if (newEmail == null || newEmail.trim().isEmpty() || !newEmail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new UserValidationException("Invalid email format");
        }
        User user = getUserById(id);
        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    @Override
    public User updateFullName(long id, String newFullName) {
        if (newFullName == null || newFullName.trim().isEmpty() || newFullName.length() < 2 || newFullName.length() > 100) {
            throw new UserValidationException("Full name must be between 2 and 100 characters and cannot be empty");
        }
        User user = getUserById(id);
        user.setFullName(newFullName);
        return userRepository.save(user);
    }

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