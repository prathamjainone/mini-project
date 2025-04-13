package io.github.prathamjainone.productivity.users.controller;

import io.github.prathamjainone.productivity.users.model.User;
import io.github.prathamjainone.productivity.users.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing users in the Shopkart application.
 * Provides endpoints for CRUD operations on users.
 */
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("databaseUserService") UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     *
     * @param user The user information to create
     * @return The created user with HTTP status 201 (created)
     */
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    /**
     * Retrieves all users from the system.
     *
     * @return ResponseEntity containing a list of all users with HTTP status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param id the unique identifier of the user
     * @return ResponseEntity containing the user with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the user doesn't exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable @Positive long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to search for
     * @return ResponseEntity containing the user with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if no user with the given username exists
     */
    @GetMapping("/byUsername")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates the password of a specific user.
     *
     * @param id        the unique identifier of the user to update
     * @param newPassword the new password (must be at least 6 characters)
     * @return ResponseEntity containing the updated user with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the user doesn't exist
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<?> updateUserPassword(
            @PathVariable @Positive long id,
            @RequestParam @Size(min = 6) String newPassword
    ) {
        User user = userService.updateUserPassword(id, newPassword);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates the username of a specific user.
     *
     * @param id        the unique identifier of the user to update
     * @param newUsername the new username (must be between 3 and 20 characters)
     * @return ResponseEntity containing the updated user with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the user doesn't exist
     */
    @PutMapping("/{id}/username")
    public ResponseEntity<?> updateUsername(
            @PathVariable @Positive long id,
            @RequestParam @Size(min = 3, max = 20) String newUsername
    ) {
        User user = userService.updateUsername(id, newUsername);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates the email of a specific user.
     *
     * @param id      the unique identifier of the user to update
     * @param newEmail the new email (must be a valid email format)
     * @return ResponseEntity containing the updated user with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the user doesn't exist
     */
    @PutMapping("/{id}/email")
    public ResponseEntity<?> updateEmail(
            @PathVariable @Positive long id,
            @RequestParam @Email String newEmail
    ) {
        User user = userService.updateEmail(id, newEmail);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates the full name of a specific user.
     *
     * @param id        the unique identifier of the user to update
     * @param newFullName the new full name (must be between 2 and 100 characters)
     * @return ResponseEntity containing the updated user with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the user doesn't exist
     */
    @PutMapping("/{id}/fullName")
    public ResponseEntity<?> updateFullName(
            @PathVariable @Positive long id,
            @RequestParam @Size(min = 2, max = 100) String newFullName
    ) {
        User user = userService.updateFullName(id, newFullName);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Deletes a specific user from the system.
     *
     * @param id the unique identifier of the user to delete
     * @return ResponseEntity with a success message and HTTP status 200 (OK) if deleted,
     * or an error message and HTTP status 404 (Not Found) if the user doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable @Positive long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}