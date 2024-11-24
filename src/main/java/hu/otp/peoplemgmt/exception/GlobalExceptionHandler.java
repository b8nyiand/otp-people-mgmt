package hu.otp.peoplemgmt.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Global exception handler that intercepts and processes exceptions thrown by controllers.
 * @author Andras Nyilas
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException and returns a structured error response with HTTP status 400 (Bad Request).
     *
     * @param e the IllegalArgumentException that was thrown.
     * @param request the HttpServletRequest associated with the exception.
     * @return a ResponseEntity containing an ErrorResponse and HTTP status 400.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "An error occurred while parsing one of your arguments, which was illegal.",
                String.format("Path: %s, Error: %s", request.getRequestURI(), e.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles OptimisticLockingFailureException and returns a structured error response with HTTP status 500 (Internal Server Error).
     *
     * @param e the OptimisticLockingFailureException that was thrown.
     * @param request the HttpServletRequest associated with the exception.
     * @return a ResponseEntity containing an ErrorResponse and HTTP status 500.
     */
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLockingFailureException(IllegalArgumentException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "An error occurred while trying to save an entity.",
                String.format("Path: %s, Error: %s", request.getRequestURI(), e.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles RuntimeException and returns a structured error response with HTTP status 500 (Internal Server Error).
     *
     * @param e the RuntimeException that was thrown.
     * @param request the HttpServletRequest associated with the exception.
     * @return a ResponseEntity containing an ErrorResponse and HTTP status 500.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "An unexpected error occurred.",
                String.format("Path: %s, Error: %s", request.getRequestURI(), e.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
