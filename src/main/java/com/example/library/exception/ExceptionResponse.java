package com.example.library.exception;

import java.util.Date;
import java.util.List;

public class ExceptionResponse {
    private final Date timestamp;
    private final List<String> errors;

    public ExceptionResponse(Date timestamp, List<String> errors) {
        this.timestamp = timestamp;
        this.errors = errors;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public List<String> getErrors() {
        return errors;
    }
}
