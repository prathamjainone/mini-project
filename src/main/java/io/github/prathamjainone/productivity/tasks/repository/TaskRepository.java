package io.github.prathamjainone.productivity.tasks.repository;

import io.github.prathamjainone.productivity.tasks.model.Task;
import io.github.prathamjainone.productivity.tasks.model.Task.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing {@link Task} entities in the database.
 * <p>
 * This interface extends {@link JpaRepository} which provides standard CRUD operations:
 * <ul>
 *     <li>{@code save(Task entity)}: Saves a task entity to the database</li>
 *     <li>{@code findById(Long id)}: Finds a task by its ID</li>
 *     <li>{@code findAll()}: Retrieves all tasks</li>
 *     <li>{@code deleteById(Long id)}: Deletes a task by its ID</li>
 *     <li>{@code delete(Task entity)}: Deletes a specific task entity</li>
 *     <li>{@code count()}: Returns the total number of tasks</li>
 *     <li>{@code existsById(Long id)}: Checks if a task with the given ID exists</li>
 * </ul>
 * <p>
 * The {@code @Repository} annotation marks this interface as a Spring Data repository,
 * which allows Spring to automatically detect and manage it.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Finds all tasks with a specific priority level.
     *
     * @param priority the priority level to filter by (LOW, MEDIUM, HIGH)
     * @return a list of tasks with the given priority
     */
    List<Task> findByPriority(Priority priority);

    /**
     * Finds all tasks that are due before a specific date and time.
     *
     * @param dateTime the cutoff LocalDateTime
     * @return a list of tasks due before the specified time
     */
    List<Task> findByDueDateBefore(LocalDateTime dateTime);

    /**
     * Finds all tasks that have a title containing the specified keyword, ignoring case.
     *
     * @param keyword the keyword to search for in task titles
     * @return a list of matching tasks
     */
    List<Task> findByTitleContainingIgnoreCase(String keyword);
}
