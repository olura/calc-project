package ru.nau.calcProjects.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(violation -> new Violation(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ValidationErrorResponse handlePriceNotFoundException(PriceNotFoundException ex) {
        Violation violation = new Violation("id", ex.getMessage());
        return new ValidationErrorResponse(List.of(violation));
    }

    @ExceptionHandler(ClientExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ValidationErrorResponse handleClientExistException(Exception ex) {
        Violation violation = new Violation("id", ex.getMessage());
        return new ValidationErrorResponse(List.of(violation));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ValidationErrorResponse handleRuntimeException(DataIntegrityViolationException ex) {
        String text = "Удалите невозможно, сначала необходимо удалить связаные с объектом сущности";
        Violation violation = new Violation("id", text);
        return new ValidationErrorResponse(List.of(violation));
    }

    @ExceptionHandler(ClientNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ValidationErrorResponse handleClientNotFoundException(ClientNotFoundException ex) {
        Violation violation = new Violation("id", ex.getMessage());
        return new ValidationErrorResponse(List.of(violation));
    }

    @ExceptionHandler(NotActualPriceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ValidationErrorResponse handleNotActualPriceException(NotActualPriceException ex) {
        Violation violation = new Violation("id", ex.getMessage());
        return new ValidationErrorResponse(List.of(violation));
    }
}