package io.github.prathamjainone.productivity.users.exceptions;

/**
 * Custom exception thrown when a user is not found.
 * This exception is used to signal that a user with a specified ID was not found in the system.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructor for creating a UserNotFoundException with a custom message.
     *
     * @param message the detail message about the exception
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
