package com.ztp.notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
public class NotesExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<NotesErrorResponse> handleException(NotesException exc) {
        NotesErrorResponse response = new NotesErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<NotesErrorResponse> handleConstraintViolationException(ConstraintViolationException exc) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = exc.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            message.append(violation.getMessage()).append(";");
        }
        message.delete(message.length() - 1, message.length());
        NotesErrorResponse response = new NotesErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                message.toString(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
