package io.github.prathamjainone.productivity.tasks.service;

import io.github.prathamjainone.productivity.tasks.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing Task operations in-memory.
 * Handles business logic related to Tasks using an ArrayList.
 */
@Service
public class InMemoryTaskService implements TaskService {

    private final List<Task> tasks;

    public InMemoryTaskService() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates and stores a new task.
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
     * Returns all tasks.
     *
     * @return list of all tasks
     */
    @Override
    public List<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Retrieves a task by ID.
     *
     * @param id the task ID
     * @return the task, or null if not found
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
     * Updates the title of a task.
     *
     * @param id the task ID
     * @param newTitle new title
     * @return updated task or null if not found
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
     * Updates the description of a task.
     *
     * @param id the task ID
     * @param newDescription new description
     * @return updated task or null if not found
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
     * Updates the due date of a task.
     *
     * @param id the task ID
     * @param newDueDate new due date
     * @return updated task or null if not found
     */
    @Override
    public Task updateTaskDueDate(long id, java.time.LocalDateTime newDueDate) {
        Task task = getTaskById(id);
        if (task != null) {
            task.setDueDate(newDueDate);
        }
        return task;
    }

    /**
     * Updates the priority of a task.
     *
     * @param id the task ID
     * @param newPriority new priority
     * @return updated task or null if not found
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
     * Deletes a task by ID.
     *
     * @param id the task ID
     * @return true if deleted, false otherwise
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
