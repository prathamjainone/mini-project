package io.github.prathamjainone.productivity.tasks.service;

import io.github.prathamjainone.productivity.tasks.exceptions.TaskNotFoundException;
import io.github.prathamjainone.productivity.tasks.exceptions.TaskValidationException;
import io.github.prathamjainone.productivity.tasks.model.Task;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface defining the operations for managing {@link Task} entities.
 * <p>
 * Provides a contract for creating, retrieving, updating, and deleting tasks,
 * along with validation and error handling.
 */
public interface TaskService {

    /**
     * Creates a new task.
     *
     * @param task the task to create
     * @return the created task
     * @throws TaskValidationException if the task is invalid or null
     */
    Task createTask(Task task) throws TaskValidationException;

    /**
     * Retrieves all tasks.
     *
     * @return a list of all tasks
     * @throws TaskNotFoundException if no tasks are found
     */
    List<Task> getAllTasks() throws TaskNotFoundException;

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task
     * @return the found task
     * @throws TaskNotFoundException if the task does not exist
     */
    Task getTaskById(long id) throws TaskNotFoundException;

    /**
     * Updates the title of a task.
     *
     * @param id       the ID of the task to update
     * @param newTitle the new title to set
     * @return the updated task
     * @throws TaskValidationException if the new title is null or empty
     */
    Task updateTaskTitle(long id, String newTitle) throws TaskValidationException;

    /**
     * Updates the description of a task.
     *
     * @param id             the ID of the task to update
     * @param newDescription the new description to set
     * @return the updated task
     * @throws TaskValidationException if the new description is null or empty
     */
    Task updateTaskDescription(long id, String newDescription) throws TaskValidationException;

    /**
     * Updates the due date of a task.
     *
     * @param id         the ID of the task to update
     * @param newDueDate the new due date to set
     * @return the updated task
     * @throws TaskValidationException if the due date is null or in the past
     */
    Task updateTaskDueDate(long id, LocalDateTime newDueDate) throws TaskValidationException;

    /**
     * Updates the priority of a task.
     *
     * @param id          the ID of the task to update
     * @param newPriority the new priority to set
     * @return the updated task
     * @throws TaskValidationException if the priority is null
     */
    Task updateTaskPriority(long id, Task.Priority newPriority) throws TaskValidationException;

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     * @return {@code true} if the task was successfully deleted
     * @throws TaskNotFoundException if the task does not exist
     */
    boolean deleteTask(long id) throws TaskNotFoundException;
}
