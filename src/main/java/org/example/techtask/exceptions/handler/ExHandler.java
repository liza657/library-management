package org.example.techtask.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.techtask.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(e -> errorMap.put(e.getField(), e.getDefaultMessage()));
        return errorMap;
    }


    @ExceptionHandler(EntityNotExistsException.class)
    protected ResponseEntity<Object> handleEntityNotExists(
            EntityNotExistsException exception) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }


    @ExceptionHandler(EntityIsNotAvailableException.class)
    protected ResponseEntity<Object> handleEntityIsNotAvailable(
            EntityIsNotAvailableException exception) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(LimitExceededException.class)
    protected ResponseEntity<Object> handleLimitExceeded(
            LimitExceededException exception) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(BookReturnRequiredException.class)
    protected ResponseEntity<Object> handleBookReturnRequired(
            BookReturnRequiredException exception) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(InvalidReturnException.class)
    protected ResponseEntity<Object> handleInvalidReturn(
            InvalidReturnException exception) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
    @ExceptionHandler(BookBorrowedException.class)
    protected ResponseEntity<Object> handleBookBorrowed(
            BookBorrowedException exception) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
    }


    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String exception) {
        ErrorMessage apiError = new ErrorMessage(status);
        apiError.setMessage(exception);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
