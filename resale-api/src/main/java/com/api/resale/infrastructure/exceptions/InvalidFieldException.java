package com.api.resale.infrastructure.exceptions;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class InvalidFieldException extends APICustomException {

    private static final String DEFAULT_MESSAGE_ERROR = "Invalid fields";

    private static final String DEFAULT_MESSAGE_ERROR_SINGLE = "Invalid field: ";

    public InvalidFieldException(HashMap<String, String> errors) {
        super(DEFAULT_MESSAGE_ERROR, errors, HttpStatus.BAD_REQUEST);
    }

    public InvalidFieldException(String mensagemCampo) {
        super(DEFAULT_MESSAGE_ERROR_SINGLE + mensagemCampo, HttpStatus.BAD_REQUEST);
    }
}
