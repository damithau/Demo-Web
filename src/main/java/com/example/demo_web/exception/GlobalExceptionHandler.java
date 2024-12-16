package com.example.demo_web.exception;

import com.example.demo_web.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Global exception handler for managing exceptions thrown by the application.
 * Handles specific exceptions like generic exceptions, resource not found, and illegal argument exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Handles generic exceptions that are not explicitly caught by other handlers.
     * @param ex The exception that was thrown.
     * @param request The current web request that caused the exception.
     * @return A ResponseEntity with the error details and a status of 500 (INTERNAL_SERVER_ERROR).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Handles resource not found exceptions.
     * @param ex The ResourceNotFoundException that was thrown.
     * @param request The current web request that caused the exception.
     * @return A ResponseEntity with the error details and a status of 404 (NOT_FOUND).
     * @throws ResourceNotFoundException if the resource is not found.
     */

    // Handle resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles illegal argument exceptions, usually caused by invalid input from the user.
     * @param ex The IllegalArgumentException that was thrown.
     * @param request The current web request that caused the exception.
     * @return A ResponseEntity with the error details and a status of 400 (BAD_REQUEST).
     * @throws IllegalArgumentException if the provided arguments are invalid.
     */

//    // Handle invalid input
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handles contract not found exceptions.
     * @param ex The ContractNotFoundException that was thrown.
     * @return A ResponseEntity with a message from the exception and a status of 404 (NOT_FOUND).
     * @throws ContractNotFoundException if the contract with the specified ID is not found.
     */

    @ExceptionHandler(ContractNotFoundException.class)
    public ResponseEntity<String> handleContractNotFound(ContractNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }










}
