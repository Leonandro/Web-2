package com.blood.bloodbackend.model.DTO;

public class ErrorResponseDTO {

    String message;
    String cause;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(String message, String cause) {
        this.message = message;
        this.cause = cause;
    }

    public ErrorResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
