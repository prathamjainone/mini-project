package io.github.prathamjainone.productivity.tasks.controller;

import io.github.prathamjainone.productivity.tasks.model.Task;
import io.github.prathamjainone.productivity.tasks.service.TaskService;
import io.github.prathamjainone.productivity.tasks.exceptions.TaskNotFoundException;
import io.github.prathamjainone.productivity.tasks.exceptions.TaskValidationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for managing tasks in the Productivity application.
 * Provides endpoints for CRUD operations on tasks including creation, retrieval, update, and deletion.
 */
@RestController
@RequestMapping("/api/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;

    /**
     * Constructs a TaskController with the specified task service.
     *
     * @param taskService the service to handle task operations
     */
    @Autowired
    public TaskController(@Qualifier("databaseTaskService") TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Creates a new task.
     *
     * @param task The task to be created
     * @return The created task with HTTP status 201 (Created)
     * @throws TaskValidationException if the task data is invalid
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) throws TaskValidationException {
        Task savedTask = taskService.createTask(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    /**
     * Retrieves all tasks.
     *
     * @return A list of all tasks with HTTP status 200 (OK)
     * @throws TaskNotFoundException if no tasks are found
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() throws TaskNotFoundException {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Retrieves a specific task by its ID.
     *
     * @param id The unique ID of the task
     * @return The task if found with HTTP status 200 (OK), otherwise 404 (Not Found)
     * @throws TaskNotFoundException if the task is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable @Positive long id) throws TaskNotFoundException {
        Task task = taskService.getTaskById(id);
        if (task != null) {
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates the title of a task.
     *
     * @param id    The ID of the task to update
     * @param title The new title
     * @return The updated task or 404 if the task does not exist
     * @throws TaskValidationException if the title is invalid
     */
    @PutMapping("/{id}/title")
    public ResponseEntity<Task> updateTaskTitle(
            @PathVariable @Positive long id,
            @RequestParam String title
    ) throws TaskValidationException {
        Task task = taskService.updateTaskTitle(id, title);
        if (task != null) {
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates the description of a task.
     *
     * @param id          The ID of the task to update
     * @param description The new description
     * @return The updated task or 404 if the task does not exist
     * @throws TaskValidationException if the description is invalid
     */
    @PutMapping("/{id}/description")
    public ResponseEntity<Task> updateTaskDescription(
            @PathVariable @Positive long id,
            @RequestParam String description
    ) throws TaskValidationException {
        Task task = taskService.updateTaskDescription(id, description);
        if (task != null) {
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates the due date of a task.
     *
     * @param id      The ID of the task to update
     * @param dueDate The new due date
     * @return The updated task or 404 if the task does not exist
     * @throws TaskValidationException if the due date is invalid
     */
    @PutMapping("/{id}/dueDate")
    public ResponseEntity<Task> updateTaskDueDate(
            @PathVariable @Positive long id,
            @RequestParam LocalDateTime dueDate
    ) throws TaskValidationException {
        Task task = taskService.updateTaskDueDate(id, dueDate);
        if (task != null) {
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates the priority of a task.
     *
     * @param id       The ID of the task to update
     * @param priority The new priority
     * @return The updated task or 404 if the task does not exist
     * @throws TaskValidationException if the priority is invalid
     */
    @PutMapping("/{id}/priority")
    public ResponseEntity<Task> updateTaskPriority(
            @PathVariable @Positive long id,
            @RequestParam Task.Priority priority
    ) throws TaskValidationException {
        Task task = taskService.updateTaskPriority(id, priority);
        if (task != null) {
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id The ID of the task to delete
     * @return Success message with HTTP status 200 (OK), or 404 (Not Found) if task does not exist
     * @throws TaskNotFoundException if the task is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable @Positive long id) throws TaskNotFoundException {
        boolean deleted = taskService.deleteTask(id);
        if (deleted) {
            return ResponseEntity.ok("Task deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
    }
}

