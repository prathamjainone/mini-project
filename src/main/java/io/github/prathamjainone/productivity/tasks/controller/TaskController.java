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
 * Provides endpoints for CRUD operations on tasks.
 */
@RestController
@RequestMapping("/api/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(@Qualifier("databaseTaskService") TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Creates a new task.
     *
     * @param task The task information to create
     * @return The created task with HTTP status 201 (created)
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) throws TaskValidationException {
        Task savedTask = taskService.createTask(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    /**
     * Retrieves all tasks from the system.
     *
     * @return ResponseEntity containing a list of all tasks with HTTP status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() throws TaskNotFoundException {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Retrieves a specific task by its ID.
     *
     * @param id the unique identifier of the task
     * @return ResponseEntity containing the task with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the task doesn't exist
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
     * Updates the title of a specific task.
     *
     * @param id    the unique identifier of the task to update
     * @param title the new title of the task (must not be blank)
     * @return ResponseEntity containing the updated task with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the task doesn't exist
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
     * Updates the description of a specific task.
     *
     * @param id          the unique identifier of the task to update
     * @param description the new description of the task
     * @return ResponseEntity containing the updated task with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the task doesn't exist
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
     * Updates the due date of a specific task.
     *
     * @param id       the unique identifier of the task to update
     * @param dueDate the new due date of the task
     * @return ResponseEntity containing the updated task with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the task doesn't exist
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
     * Updates the priority of a specific task.
     *
     * @param id       the unique identifier of the task to update
     * @param priority the new priority of the task
     * @return ResponseEntity containing the updated task with HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the task doesn't exist
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
     * Deletes a specific task from the system.
     *
     * @param id the unique identifier of the task to delete
     * @return ResponseEntity with a success message and HTTP status 200 (OK) if deleted,
     * or an error message and HTTP status 404 (Not Found) if the task doesn't exist
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
