package io.github.prathamjainone.productivity.tasks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Global exception handler for the application.
 * Provides consistent error responses across all controller methods.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles TaskNotFoundException and returns a 404 Not Found response.
     *
     * @param exception The exception that was thrown (TaskNotFoundException).
     * @return A ResponseEntity of ErrorResponse with error details.
     */
    @ExceptionHandler(value = TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                "Task Not Found",
                exception.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles TaskValidationException and returns a 400 Bad Request response.
     *
     * @param exception The exception that was thrown (TaskValidationException).
     * @return A ResponseEntity of ErrorResponse with error details.
     */
    @ExceptionHandler(value = TaskValidationException.class)
    public ResponseEntity<ErrorResponse> handleTaskValidationException(TaskValidationException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "Bad Request",
                exception.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation errors from @Valid annotations and returns field specific error messages.
     *
     * @param exception The exception containing validation errors.
     * @return ResponseEntity with detailed validation errors.
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "Validation Error",
                exception.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Fallback exception handler for all other exceptions.
     * @param exception The exception that was thrown.
     * @return ResponseEntity with generic error details.
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                "Internal Server Error",
                exception.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Error response structure with timestamp, status, message and details.
     */
    private record ErrorResponse(int status, LocalDateTime timestamp, String message, String details) {
    }
}
