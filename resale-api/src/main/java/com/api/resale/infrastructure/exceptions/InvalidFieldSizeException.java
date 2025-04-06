package com.api.resale.infrastructure.exceptions;

public class InvalidFieldSizeException extends InvalidFieldException {

    public InvalidFieldSizeException(String field, Integer max, Integer min) {
        super(field + " must contain at least " + max + " and at most " + min + " characters");
    }

    public InvalidFieldSizeException(String field, Integer size) {
        super(field + " must contain at least " + size + " caracteres");
    }

    public InvalidFieldSizeException(String field, Integer size, Boolean onlyMax) {
        super(field + " must contain a maximum of " + size + " characters");
    }
}
