package io.github.prathamjainone.productivity.tasks.service;

import io.github.prathamjainone.productivity.tasks.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory implementation of the {@link TaskService} interface.
 * <p>
 * This service manages {@link Task} objects using a local {@link ArrayList},
 * making it useful for testing or development environments without a database.
 */
@Service
public class InMemoryTaskService implements TaskService {

    private final List<Task> tasks;

    /**
     * Constructs a new {@code InMemoryTaskService} with an empty task list.
     */
    public InMemoryTaskService() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates and stores a new task in memory.
     *
     * @param task the task to create
     * @return the created task
     */
    @Override
    public Task createTask(Task task) {
        tasks.add(task);
        return task;
    }

    /**
     * Retrieves all stored tasks.
     *
     * @return a list of all tasks
     */
    @Override
    public List<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task
     * @return the task if found, or {@code null} if not found
     */
    @Override
    public Task getTaskById(long id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    /**
     * Updates the title of the specified task.
     *
     * @param id       the ID of the task to update
     * @param newTitle the new title to set
     * @return the updated task, or {@code null} if not found
     */
    @Override
    public Task updateTaskTitle(long id, String newTitle) {
        Task task = getTaskById(id);
        if (task != null) {
            task.setTitle(newTitle);
        }
        return task;
    }

    /**
     * Updates the description of the specified task.
     *
     * @param id             the ID of the task to update
     * @param newDescription the new description to set
     * @return the updated task, or {@code null} if not found
     */
    @Override
    public Task updateTaskDescription(long id, String newDescription) {
        Task task = getTaskById(id);
        if (task != null) {
            task.setDescription(newDescription);
        }
        return task;
    }

    /**
     * Updates the due date of the specified task.
     *
     * @param id         the ID of the task to update
     * @param newDueDate the new due date to set
     * @return the updated task, or {@code null} if not found
     */
    @Override
    public Task updateTaskDueDate(long id, LocalDateTime newDueDate) {
        Task task = getTaskById(id);
        if (task != null) {
            task.setDueDate(newDueDate);
        }
        return task;
    }

    /**
     * Updates the priority of the specified task.
     *
     * @param id          the ID of the task to update
     * @param newPriority the new priority to set
     * @return the updated task, or {@code null} if not found
     */
    @Override
    public Task updateTaskPriority(long id, Task.Priority newPriority) {
        Task task = getTaskById(id);
        if (task != null) {
            task.setPriority(newPriority);
        }
        return task;
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     * @return {@code true} if the task was found and deleted, {@code false} otherwise
     */
    @Override
    public boolean deleteTask(long id) {
        Task task = getTaskById(id);
        if (task != null) {
            tasks.remove(task);
            return true;
        }
        return false;
    }
}
