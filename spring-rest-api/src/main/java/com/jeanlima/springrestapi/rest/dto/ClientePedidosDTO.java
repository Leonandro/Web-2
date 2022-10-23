package com.jeanlima.springrestapi.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeanlima.springrestapi.model.Cliente;
import com.jeanlima.springrestapi.model.Pedido;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

public class ClientePedidosDTO {

    private Integer id;

    private String nome;

    private String cpf;

    //um cliente pode ter muitos pedidos
    /*
     * mappedBy --> para a partir da classe cliente eu possa mapear/retornar os pedidos
     * passo como parametro o campo, na tabela de pedidos, que faz referencia a cliente.
     * no caso é cliente.
     *
     * fetch  --> Lazy ou Eager - CUIDADO!!!
     */

    private List<Pedido> pedidos;

    public ClientePedidosDTO() {
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

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
