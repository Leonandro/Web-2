package com.blood.bloodbackend.controller;


import com.blood.bloodbackend.exception.UsuarioNaoAutorizadoException;
import com.blood.bloodbackend.model.DTO.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerAdvice {

    //Em caso de uma exceção não capturada, retorna um DTO de erro para uma resposta mais adequado
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseDTO handleException (HttpServletRequest request, Exception e) {
        return new ErrorResponseDTO("A request " + request.getMethod() + " " + request.getRequestURI() +
                " acabou gerando uma exceção, com causa descrita no campo de causa", e.getMessage());
    }
}
