package io.github.prathamjainone.productivity.tasks.exceptions;

/**
 * Exception thrown when a task fails validation checks.
 *
 * <p>This exception extends {@link RuntimeException} and indicates that the task input
 * did not meet one or more expected validation constraints.</p>
 */
public class TaskValidationException extends RuntimeException {

    /**
     * Constructs a new {@code TaskValidationException} with the specified detail message.
     *
     * @param message the detail message providing information about the validation failure
     */
    public TaskValidationException(String message) {
        super(message);
    }
}
