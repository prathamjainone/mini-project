package io.github.prathamjainone.productivity.tasks.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a task entity with attributes such as title, description,
 * due date, and priority. This entity is mapped to a database table using JPA.
 */
@Entity
@Table(name = "tasks")
public class Task {

    /**
     * The unique identifier for the task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the task.
     * Cannot be blank.
     */
    @NotBlank(message = "Title is required.")
    @Column(nullable = false)
    private String title;

    /**
     * The description of the task.
     * Optional, but must be at most 500 characters.
     */
    @Size(max = 500, message = "Description cannot be more than 500 characters.")
    @Column(length = 500)
    private String description;

    /**
     * The due date and time by which the task should be completed.
     * Must be in the present or future.
     */
    @FutureOrPresent(message = "Due date must be in the present or future.")
    private LocalDateTime dueDate;

    /**
     * The priority level of the task.
     * Cannot be null.
     */
    @NotNull(message = "Priority is required.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    /**
     * Whether the task is completed or not.
     */
    @Column(nullable = false)
    private boolean completed = false;

    /**
     * Enum representing priority levels for a task.
     */
    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    /**
     * Default no-args constructor.
     */
    public Task() {
    }

    /**
     * Constructs a new Task with the specified details.
     *
     * @param title       the title of the task
     * @param description the description of the task
     * @param dueDate     the due date and time
     * @param priority    the priority level
     */
    public Task(String title, String description, LocalDateTime dueDate, Priority priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = false;
    }

    // Getters and Setters

    /**
     * Returns the ID of the task.
     *
     * @return the task ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the task.
     *
     * @param id the task ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the title of the task.
     *
     * @return the task title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the task.
     *
     * @param title the task title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the task.
     *
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description the task description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the due date of the task.
     *
     * @return the due date and time
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the task.
     *
     * @param dueDate the due date and time
     */
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Returns the priority level of the task.
     *
     * @return the task priority
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the priority level of the task.
     *
     * @param priority the task priority
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Returns whether the task is completed or not.
     *
     * @return whether the task is completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets whether the task is completed or not.
     *
     * @param completed whether the task is completed
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if this object is the same as the {@code o} argument; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return completed == task.completed &&
                Objects.equals(id, task.id) &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(dueDate, task.dueDate) &&
                priority == task.priority;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this task
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, dueDate, priority, completed);
    }

    /**
     * Returns a string representation of the task.
     *
     * @return a string describing the task
     */
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                ", completed=" + completed +
                '}';
    }
}
