package io.github.prathamjainone.productivity.tasks.service;

import io.github.prathamjainone.productivity.tasks.exceptions.TaskNotFoundException;
import io.github.prathamjainone.productivity.tasks.exceptions.TaskValidationException;
import io.github.prathamjainone.productivity.tasks.model.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    Task createTask(Task task) throws TaskValidationException;

    List<Task> getAllTasks() throws TaskNotFoundException;

    Task getTaskById(long id) throws TaskNotFoundException;

    Task updateTaskTitle(long id, String newTitle) throws TaskValidationException;

    Task updateTaskDescription(long id, String newDescription) throws TaskValidationException;

    Task updateTaskDueDate(long id, LocalDateTime newDueDate) throws TaskValidationException;

    Task updateTaskPriority(long id, Task.Priority newPriority) throws TaskValidationException;

    boolean deleteTask(long id) throws TaskNotFoundException;
}
