package com.api.rest.encuestas.dto;

public class ValidationError {

    private String code;
    private String message;

    //Getters and Setters
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
