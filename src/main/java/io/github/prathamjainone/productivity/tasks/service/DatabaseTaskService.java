package io.github.prathamjainone.productivity.tasks.service;

import io.github.prathamjainone.productivity.tasks.exceptions.TaskNotFoundException;
import io.github.prathamjainone.productivity.tasks.exceptions.TaskValidationException;
import io.github.prathamjainone.productivity.tasks.model.Task;
import io.github.prathamjainone.productivity.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A service that uses a database to store and manage task information.
 * This class implements the TaskService interface and uses a TaskRepository
 * to perform operations on the database.
 */
@Service
public class DatabaseTaskService implements TaskService {
    /**
     * The repository used to access the database.
     */
    private final TaskRepository taskRepository;

    /**
     * Creates a new DatabaseTaskService with the provided repository.
     *
     * @param taskRepository the repository to use for database operations
     */
    @Autowired
    public DatabaseTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Adds a new task to the database.
     *
     * @param task the task to add
     * @return the saved task with any database-generated values (like ID)
     */
    @Override
    public Task createTask(Task task) {
        if (task == null) {
            throw new TaskValidationException("Task must not be null");
        }
        return taskRepository.save(task);
    }

    /**
     * Gets a list of all tasks from the database.
     *
     * @return a list containing all tasks
     */
    @Override
    public List<Task> getAllTasks() {
        List<Task> taskList = taskRepository.findAll();
        if (taskList.isEmpty()) {
            throw new TaskNotFoundException("No tasks found");
        }
        return taskList;
    }

    /**
     * Finds a task in the database using its ID.
     *
     * @param id the ID of the task to find
     * @return the found task
     */
    @Override
    public Task getTaskById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    /**
     * Updates the title of a task in the database.
     *
     * @param id the ID of the task to update
     * @param newTitle the new title to set
     * @return the updated task
     */
    @Override
    public Task updateTaskTitle(long id, String newTitle) {
        if (newTitle == null || newTitle.isEmpty()) {
            throw new TaskValidationException("Title must not be null or empty");
        }
        Task task = getTaskById(id);
        task.setTitle(newTitle);
        return taskRepository.save(task);
    }

    /**
     * Updates the description of a task in the database.
     *
     * @param id the ID of the task to update
     * @param newDescription the new description to set
     * @return the updated task
     */
    @Override
    public Task updateTaskDescription(long id, String newDescription) {
        if (newDescription == null || newDescription.isEmpty()) {
            throw new TaskValidationException("Description must not be null or empty");
        }
        Task task = getTaskById(id);
        task.setDescription(newDescription);
        return taskRepository.save(task);
    }

    /**
     * Updates the due date of a task in the database.
     *
     * @param id the ID of the task to update
     * @param newDueDate the new due date to set
     * @return the updated task
     */
    @Override
    public Task updateTaskDueDate(long id, LocalDateTime newDueDate) {
        if (newDueDate == null || newDueDate.isBefore(LocalDateTime.now())) {
            throw new TaskValidationException("Due date must be in the future");
        }
        Task task = getTaskById(id);
        task.setDueDate(newDueDate);
        return taskRepository.save(task);
    }

    /**
     * Updates the priority of a task in the database.
     *
     * @param id the ID of the task to update
     * @param newPriority the new priority to set
     * @return the updated task
     */
    @Override
    public Task updateTaskPriority(long id, Task.Priority newPriority) {
        if (newPriority == null) {
            throw new TaskValidationException("Priority must not be null");
        }
        Task task = getTaskById(id);
        task.setPriority(newPriority);
        return taskRepository.save(task);
    }

    /**
     * Deletes a task from the database.
     *
     * @param id the ID of the task to delete
     * @return true if the task was deleted
     */
    @Override
    public boolean deleteTask(long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        } else {
            throw new TaskNotFoundException("Task with id " + id + " not found");
        }
    }
}
