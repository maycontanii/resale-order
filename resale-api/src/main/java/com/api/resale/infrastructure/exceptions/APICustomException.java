package com.api.resale.infrastructure.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Data
public class APICustomException extends RuntimeException {

    private int status;
    private String path;
    private String solution;
    private String exception;
    private HashMap<String, String> errors;

    public APICustomException() {
        super("An unexpected error occurred. If the problem persists, please contact the platform administrators.");
        this.status = HttpStatus.BAD_REQUEST.value();
        this.exception = this.getClass().getSimpleName();
    }

    public APICustomException(String erro) {
        super(erro);
        this.status = HttpStatus.BAD_REQUEST.value();
        this.exception = this.getClass().getSimpleName();
    }

    public APICustomException(String erro, HttpStatus httpStatus) {
        super(erro);
        this.status = httpStatus.value();
        this.exception = this.getClass().getSimpleName();
    }

    public APICustomException(String erro, HashMap<String, String> errors, HttpStatus httpStatus) {
        super(erro);
        this.status = httpStatus.value();
        this.exception = this.getClass().getSimpleName();
        this.errors = errors;
    }
}
