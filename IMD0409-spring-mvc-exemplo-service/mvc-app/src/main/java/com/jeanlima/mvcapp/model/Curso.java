package com.jeanlima.mvcapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Curso {

    private String nome;

    public Curso(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
