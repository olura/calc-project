package ru.nau.calcProjects.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<String> handlePriceNotFoundException(PriceNotFoundException ex) {
        String body = "{\"state\":\"fail\"," +
                "\"message\":\"" + ex.getMessage() + "\"}";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ClientExistException.class)
    public ResponseEntity<String> handleClientExistException(Exception ex) {
        String body = "{\"state\":\"fail\"," +
                "\"message\":\"" + ex.getMessage() + "\"}";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<String> handleRuntimeException(ValidateException ex) {
        String body = "{\"state\":\"fail\"," +
                "\"message\":\"" + ex.getMessage() + "\"}";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleRuntimeException(DataIntegrityViolationException ex) {
        String body = "{\"state\":\"fail\"," +
                "\"message\":\"Удалите невозможно, сначала необходимо удалить связаные с объектом сущности\"}";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<String> handleClientNotFoundException(ClientNotFoundException ex) {
        String body = "{\"state\":\"fail\"," +
                "\"message\":\"" + ex.getMessage() + "\"}";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}