package io.github.prathamjainone.productivity.users.exceptions;

/**
 * Custom exception thrown when a validation error occurs for a user.
 * This exception is used to signal that a user-related operation failed due to invalid input or other validation issues.
 */
public class UserValidationException extends RuntimeException {

    /**
     * Constructor for creating a UserValidationException with a custom message.
     *
     * @param message the detail message about the validation exception
     */
    public UserValidationException(String message) {
        super(message);
    }
}
