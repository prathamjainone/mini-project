package io.github.prathamjainone.productivity.users.service;

import io.github.prathamjainone.productivity.users.exceptions.UserNotFoundException;
import io.github.prathamjainone.productivity.users.exceptions.UserValidationException;
import io.github.prathamjainone.productivity.users.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user) throws UserValidationException;

    List<User> getAllUsers() throws UserNotFoundException;

    User getUserById(long id) throws UserNotFoundException;

    User getUserByUsername(String username) throws UserNotFoundException;

    User updateUserPassword(long id, String newPassword) throws UserValidationException;

    User updateUsername(long id, String newUsername) throws UserValidationException;

    User updateEmail(long id, String newEmail) throws UserValidationException;

    User updateFullName(long id, String newFullName) throws UserValidationException;

    boolean deleteUser(long id) throws UserNotFoundException;
}