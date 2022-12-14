package com.jeanlima.springrestapi.rest.dto;

import javax.persistence.Column;
import java.math.BigDecimal;

public class ProdutoAtualizacaoDTO {

    private Integer id;

    private String descricao;

    private BigDecimal preco;

    public ProdutoAtualizacaoDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
