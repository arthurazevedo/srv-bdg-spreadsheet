package com.fourbudget.spreadsheet.config.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MySystemException.class)
    public ResponseEntity<ErrorResponse> handleValidacaoException(MySystemException exception) {
        ErrorResponse error = new ErrorResponse(exception.getStatus(), exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(error);
    }
}