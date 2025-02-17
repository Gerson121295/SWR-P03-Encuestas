package com.api.rest.encuestas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)  //codigo de estado a retornar
public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    //Constructores
    public ResourceNotFoundException() {}

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
