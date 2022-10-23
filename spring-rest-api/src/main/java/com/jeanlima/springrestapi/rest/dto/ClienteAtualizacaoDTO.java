package com.jeanlima.springrestapi.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeanlima.springrestapi.model.Pedido;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

public class ClienteAtualizacaoDTO {

    private Integer id;

    private String nome;

    private String cpf;

    private List<Integer> pedidos;

    public ClienteAtualizacaoDTO() {
    }

    public ClienteAtualizacaoDTO(Integer id) {
        this.id = id;
    }

    public ClienteAtualizacaoDTO(Integer id, String nome, String cpf, List<Integer> pedidos) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.pedidos = pedidos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Integer> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Integer> pedidos) {
        this.pedidos = pedidos;
    }
}
