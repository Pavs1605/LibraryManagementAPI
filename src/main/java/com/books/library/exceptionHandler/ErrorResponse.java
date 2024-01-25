package com.books.library.exceptionHandler;

import com.books.library.exception.BookNotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private  LocalDateTime timestamp;
    private  int status;
    private  HttpStatus error;
    private  String message;

    private  String path;
    private  List<String> validationErrors;
    public ErrorResponse(HttpStatus error, String message, RuntimeException ex, String path, List<String> validationErrors) {
        this.timestamp = LocalDateTime.now();
        this.status = error.value();
        this.error = error;
        this.message = message;
        this.path = path;
        this.validationErrors = validationErrors;

    }

    public ErrorResponse(HttpStatus error, String message, Exception ex, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = error.value();
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public HttpStatus getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}