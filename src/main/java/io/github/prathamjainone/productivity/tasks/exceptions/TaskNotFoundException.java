package io.github.prathamjainone.productivity.tasks.exceptions;

/**
 * Exception thrown when a Task is not found in the database or storage.
 * <p>
 * This exception is typically used in service or repository layers when
 * an operation fails to locate a task with the specified identifier.
 * </p>
 */
public class TaskNotFoundException extends RuntimeException {

    /**
     * Constructs a new TaskNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public TaskNotFoundException(String message) {
        super(message);
    }
}
