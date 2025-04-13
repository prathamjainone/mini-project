package io.github.prathamjainone.productivity.users.service;

import io.github.prathamjainone.productivity.users.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory service class for managing User operations.
 * This class handles business logic related to Users, storing data in memory.
 */
@Service
public class InMemoryUserService implements UserService {

    private final List<User> users;

    public InMemoryUserService() {
        this.users = new ArrayList<>();
    }

    @Override
    public User createUser(User user) {
        users.add(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User getUserById(long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User updateUserPassword(long id, String newPassword) {
        User user = getUserById(id);
        if (user != null) {
            user.setPassword(newPassword);
        }
        return user;
    }

    @Override
    public User updateUsername(long id, String newUsername) {
        User user = getUserById(id);
        if (user != null) {
            user.setUsername(newUsername);
        }
        return user;
    }

    @Override
    public User updateEmail(long id, String newEmail) {
        User user = getUserById(id);
        if (user != null) {
            user.setEmail(newEmail);
        }
        return user;
    }

    @Override
    public User updateFullName(long id, String newFullName) {
        User user = getUserById(id);
        if (user != null) {
            user.setFullName(newFullName);
        }
        return user;
    }

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