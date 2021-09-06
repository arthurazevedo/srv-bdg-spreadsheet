package com.fourbudget.spreadsheet.config.error;

import com.fourbudget.spreadsheet.config.validation.FormError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandler {

    private final String ERROR_DATABASE = "Database error.";

    @ExceptionHandler({MySystemException.class, MethodArgumentNotValidException.class})
    protected ResponseEntity<?> handleException(Exception exception) {
        if (exception instanceof MethodArgumentNotValidException) {
            return handleNotValid((MethodArgumentNotValidException) exception);
        }

        return handleErrors((MySystemException) exception);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ErrorResponse> handleDataException() {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_DATABASE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private ResponseEntity<ErrorResponse> handleErrors(MySystemException exception) {
        ErrorResponse error = new ErrorResponse(exception.getStatus(), exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(error);
    }

    private ResponseEntity<List<FormError>> handleNotValid(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<FormError> formErrorDtos = new ArrayList<>();

        fieldErrors.forEach(err -> {
            FormError formError = new FormError(err.getField(), err.getDefaultMessage());
            formErrorDtos.add(formError);
        });

        return ResponseEntity.badRequest().body(formErrorDtos);
    }
}