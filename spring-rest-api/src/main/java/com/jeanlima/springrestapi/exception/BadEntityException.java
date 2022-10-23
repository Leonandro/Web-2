package com.jeanlima.springrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadEntityException extends  RuntimeException{


    public BadEntityException(String message) {
        super(message);
    }
}
