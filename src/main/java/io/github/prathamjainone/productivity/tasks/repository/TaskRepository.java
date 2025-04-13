package io.github.prathamjainone.productivity.tasks.repository;

import io.github.prathamjainone.productivity.tasks.model.Task;
import io.github.prathamjainone.productivity.tasks.model.Task.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for performing CRUD and custom operations on {@link Task} entities.
 * <p>
 * Extends {@link JpaRepository} to provide built-in functionality such as:
 * <ul>
 *     <li>{@code save(Task entity)} – Save or update a task</li>
 *     <li>{@code findById(Long id)} – Retrieve a task by its ID</li>
 *     <li>{@code findAll()} – Retrieve all tasks</li>
 *     <li>{@code deleteById(Long id)} – Delete a task by ID</li>
 *     <li>{@code delete(Task entity)} – Delete a task entity</li>
 *     <li>{@code count()} – Count the total number of tasks</li>
 *     <li>{@code existsById(Long id)} – Check if a task exists by ID</li>
 * </ul>
 * <p>
 * Annotated with {@link Repository} to enable Spring's component scanning and exception translation.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Retrieves all tasks with the specified priority.
     *
     * @param priority the priority level to filter by (LOW, MEDIUM, HIGH)
     * @return a list of tasks matching the given priority
     */
    List<Task> findByPriority(Priority priority);

    /**
     * Retrieves all tasks that are due before the specified date and time.
     *
     * @param dateTime the upper bound for task due dates
     * @return a list of tasks due before the specified date and time
     */
    List<Task> findByDueDateBefore(LocalDateTime dateTime);

    /**
     * Retrieves all tasks with titles containing the given keyword, case-insensitively.
     *
     * @param keyword the keyword to search for in task titles
     * @return a list of tasks with titles containing the keyword
     */
    List<Task> findByTitleContainingIgnoreCase(String keyword);
}
