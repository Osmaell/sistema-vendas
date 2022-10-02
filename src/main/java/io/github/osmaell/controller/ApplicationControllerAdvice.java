package io.github.osmaell.controller;

import io.github.osmaell.exception.RecursoNaoEncontradoException;
import io.github.osmaell.exception.RegraNegocioException;
import io.github.osmaell.utils.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException exception) {
        String mensagemErro = exception.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleRecursoNaoEncontradoException(RecursoNaoEncontradoException exception) {
        String mensagemErro = exception.getMessage();
        return new ApiErrors(mensagemErro);
    }
    
}