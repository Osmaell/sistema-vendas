package io.github.osmaell.utils;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    @Getter
    private List<String> errors;


    public ApiErrors(String mensagemErro) {
        this.errors = Arrays.asList(mensagemErro);
    }

}