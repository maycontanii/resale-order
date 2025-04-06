package com.api.resale.infrastructure.exceptions;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;

public class MandatoryFieldException extends APICustomException {

    private static final String DEFAULT_MESSAGE_ERROR = "Mandatory field: ";
    private static final String DEFAULT_MESSAGE_ERROR_SINGLE = "Field cannot be null or empty.";

    private final HashMap<String, String> erros = new HashMap<>();

    public MandatoryFieldException(List<String> campos) {
        super(DEFAULT_MESSAGE_ERROR, HttpStatus.BAD_REQUEST);
        campos.forEach(campo -> erros.put(campo, DEFAULT_MESSAGE_ERROR_SINGLE));
        this.setErrors(erros);
    }

    public MandatoryFieldException(HashMap<String, String> erro) {
        super(DEFAULT_MESSAGE_ERROR, HttpStatus.BAD_REQUEST);
        this.setErrors(erro);
    }

    public MandatoryFieldException(String mensagemCampo) {
        super(DEFAULT_MESSAGE_ERROR + mensagemCampo, HttpStatus.BAD_REQUEST);
    }
}
