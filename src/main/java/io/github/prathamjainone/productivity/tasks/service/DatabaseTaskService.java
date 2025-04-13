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
 * Service implementation for managing {@link Task} entities using a database.
 * <p>
 * This class interacts with the {@link TaskRepository} to perform operations like
 * creating, retrieving, updating, and deleting tasks.
 */
@Service
public class DatabaseTaskService implements TaskService {

    /**
     * Repository for accessing task data in the database.
     */
    private final TaskRepository taskRepository;

    /**
     * Constructs a new {@code DatabaseTaskService} with the specified task repository.
     *
     * @param taskRepository the task repository to be used for database operations
     */
    @Autowired
    public DatabaseTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Saves a new task to the database.
     *
     * @param task the task to be created
     * @return the saved task, including any database-generated values like ID
     * @throws TaskValidationException if the task is null
     */
    @Override
    public Task createTask(Task task) {
        if (task == null) {
            throw new TaskValidationException("Task must not be null");
        }
        return taskRepository.save(task);
    }

    /**
     * Retrieves all tasks from the database.
     *
     * @return a list of all tasks
     * @throws TaskNotFoundException if no tasks are found
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
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task
     * @return the task with the specified ID
     * @throws TaskNotFoundException if the task is not found
     */
    @Override
    public Task getTaskById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    /**
     * Updates the title of a task.
     *
     * @param id       the ID of the task to update
     * @param newTitle the new title to set
     * @return the updated task
     * @throws TaskValidationException if the new title is null or empty
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
     * Updates the description of a task.
     *
     * @param id             the ID of the task to update
     * @param newDescription the new description to set
     * @return the updated task
     * @throws TaskValidationException if the new description is null or empty
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
     * Updates the due date of a task.
     *
     * @param id          the ID of the task to update
     * @param newDueDate  the new due date to set
     * @return the updated task
     * @throws TaskValidationException if the due date is null or not in the future
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
     * Updates the priority of a task.
     *
     * @param id          the ID of the task to update
     * @param newPriority the new priority to set
     * @return the updated task
     * @throws TaskValidationException if the priority is null
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
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     * @return {@code true} if the task was found and deleted
     * @throws TaskNotFoundException if the task with the given ID does not exist
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
