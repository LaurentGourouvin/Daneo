package com.daneo.daneo.common;

import com.daneo.daneo.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );

        problem.setTitle("Resource not found");

        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setTitle("Validation error");
        problemDetail.setDetail("One or more fields are invalid");

        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        Map<String, String> hashMapError = new HashMap<>();

        for (FieldError f : errors) {
            hashMapError.put(f.getField(), f.getDefaultMessage());
        }

        problemDetail.setProperty("errors", hashMapError);

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleRuntimeException(Exception e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }
}
