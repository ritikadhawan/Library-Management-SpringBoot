package com.example.library.exception;

import javax.validation.ConstraintDeclarationException;

public class NoRecordFoundException extends ConstraintDeclarationException {
    private final String message;

    public NoRecordFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
