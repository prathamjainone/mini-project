package io.github.prathamjainone.productivity.tasks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Global exception handler for the application.
 * Provides standardized error responses for all controller methods across the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link TaskNotFoundException} and returns a 404 Not Found response.
     *
     * @param exception The thrown TaskNotFoundException
     * @return A ResponseEntity containing an ErrorResponse with relevant error details
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
     * Handles {@link TaskValidationException} and returns a 400 Bad Request response.
     *
     * @param exception The thrown TaskValidationException
     * @return A ResponseEntity containing an ErrorResponse with relevant error details
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
     * Handles validation errors from @Valid annotations and returns detailed error information.
     *
     * @param exception The MethodArgumentNotValidException containing validation errors
     * @return A ResponseEntity with a descriptive ErrorResponse for validation issues
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
     * Handles all uncaught exceptions and returns a 500 Internal Server Error response.
     *
     * @param exception The thrown Exception
     * @return A ResponseEntity containing a generic ErrorResponse
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
     * Error response data structure used in all error responses returned by this handler.
     *
     * @param status    The HTTP status code
     * @param timestamp The time the error occurred
     * @param message   A short error message
     * @param details   Detailed information about the error
     */
    private record ErrorResponse(int status, LocalDateTime timestamp, String message, String details) {
    }
}
