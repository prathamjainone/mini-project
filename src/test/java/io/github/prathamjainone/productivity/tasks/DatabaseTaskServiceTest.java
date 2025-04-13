package io.github.prathamjainone.productivity.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import io.github.prathamjainone.productivity.tasks.exceptions.TaskNotFoundException;
import io.github.prathamjainone.productivity.tasks.exceptions.TaskValidationException;
import io.github.prathamjainone.productivity.tasks.model.Task;
import io.github.prathamjainone.productivity.tasks.model.Task.Priority;
import io.github.prathamjainone.productivity.tasks.repository.TaskRepository;
import io.github.prathamjainone.productivity.tasks.service.DatabaseTaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the DatabaseTaskService class.
 * Uses Mockito to mock the repository layer.
 */
@ExtendWith(MockitoExtension.class)
class DatabaseTaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private DatabaseTaskService taskService;

    private Task testTask;

    /**
     * Set up test data before each test.
     */
    @BeforeEach
    void setUp() {
        testTask = new Task(
                "Test Task",
                "This is a test task",
                LocalDateTime.now().plusDays(1),
                Priority.MEDIUM
        );
        testTask.setId(1L);
    }

    /**
     * Test that a task can be created successfully.
     */
    @Test
    @DisplayName("Should create a task when given valid data")
    void testCreateTask() {
        // Arrange
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        // Act
        Task created = taskService.createTask(testTask);

        // Assert
        assertNotNull(created);
        assertEquals(testTask.getTitle(), created.getTitle());
        verify(taskRepository).save(testTask);
    }

    /**
     * Test that an exception is thrown when trying to create a null task.
     */
    @Test
    @DisplayName("Should throw exception when creating null task")
    void testCreateNullTask() {
        // Act & Assert
        assertThrows(TaskValidationException.class, () -> taskService.createTask(null));
        verify(taskRepository, never()).save(any());
    }

    /**
     * Test retrieving all tasks.
     */
    @Test
    @DisplayName("Should return all tasks")
    void testGetAllTasks() {
        // Arrange
        Task task2 = new Task(
                "Another Task",
                "Another test task",
                LocalDateTime.now().plusDays(2),
                Priority.HIGH
        );
        task2.setId(2L);

        when(taskRepository.findAll()).thenReturn(Arrays.asList(testTask, task2));

        // Act
        List<Task> tasks = taskService.getAllTasks();

        // Assert
        assertEquals(2, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
        assertEquals("Another Task", tasks.get(1).getTitle());
    }

    /**
     * Test that a task can be retrieved by ID.
     */
    @Test
    @DisplayName("Should return task when ID exists")
    void testGetTaskById() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));

        // Act
        Task found = taskService.getTaskById(1L);

        // Assert
        assertNotNull(found);
        assertEquals(1L, found.getId());
        assertEquals("Test Task", found.getTitle());
    }

    /**
     * Test that an exception is thrown when a task is not found by ID.
     */
    @Test
    @DisplayName("Should throw exception when task ID doesn't exist")
    void testGetTaskByIdNotFound() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(99L));
    }
}
