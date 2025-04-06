package com.api.resale.infrastructure.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

@ControllerAdvice
public class APIExceptionHandlers {

    @ResponseBody
    @ExceptionHandler(value = APICustomException.class)
    public ResponseEntity defaultErrorHandler(APICustomException exception) {
        return new ResponseEntity(ErrorResponse.builder()
                .status(exception.getStatus())
                .timestamp(getDataAtual())
                .message(exception.getMessage())
                .build(), HttpStatus.valueOf(exception.getStatus()));
    }

    @ResponseBody
    @ExceptionHandler({InvalidFormatException.class, JsonMappingException.class})
    public ResponseEntity defaultErrorHandler(Exception exception, ServletWebRequest request) {
        HttpMethod httpMethod = request.getHttpMethod();
        String method = Objects.nonNull(httpMethod) ? httpMethod.toString() : null;
        return new ResponseEntity(ErrorResponseFull.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequest().getRequestURI())
                .timestamp(getDataAtual())
                .message("Error converting field(s): " + exception.getCause().getLocalizedMessage())
                .httpMethod(method)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @Data
    @Builder
    @JsonPropertyOrder({"status", "timestamp", "message"})
    private static class ErrorResponse {
        private int status;
        private String timestamp;
        private String message;
    }

    @Data
    @Builder
    @JsonPropertyOrder({"status", "http_method", "path", "timestamp", "exception", "message", "errors"})
    private static class ErrorResponseFull {
        private int status;
        private String path;
        private String timestamp;
        private String exception;
        private String message;
        private HashMap<String, String> errors;

        @JsonProperty(value = "http_method")
        private String httpMethod;
    }

    private static String getDataAtual() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        return simpleDateFormat.format(new Date());
    }
}
