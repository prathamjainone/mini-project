package io.github.prathamjainone.productivity.tasks.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task {

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
     * The due date of the task.
     * Must be present or a future date/time.
     */
    @FutureOrPresent(message = "Due date must be in the present or future.")
    private LocalDateTime dueDate;

    /**
     * The priority of the task.
     * Cannot be null.
     */
    @NotNull(message = "Priority is required.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    /**
     * Enum representing task priority levels.
     */
    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    // Default constructor
    public Task() {
    }

    // Parameterized constructor
    public Task(String title, String description, LocalDateTime dueDate, Priority priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    // Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;
        return Objects.equals(id, task.id)
                && Objects.equals(title, task.title)
                && Objects.equals(description, task.description)
                && Objects.equals(dueDate, task.dueDate)
                && priority == task.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, dueDate, priority);
    }

    // toString method
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                '}';
    }
}
