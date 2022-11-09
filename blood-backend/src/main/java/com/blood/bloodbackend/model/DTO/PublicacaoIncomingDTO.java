package com.blood.bloodbackend.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class PublicacaoIncomingDTO {

    private long id;
    private long unidadeDoacao;
    private long usuario;
    private String nomeDonatario;

    private int imageCode;

    private long doador;

    @JsonFormat(pattern="yyyy-MM-dd")
    private  Date idadeDonatario;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dataCriacao;

    private  int prioridade;

    private int tipoSanguineo;

    public long getId() {
        return id;
    }


    public long getUnidadeDoacao() {
        return unidadeDoacao;
    }

    public void setUnidadeDoacao(long unidadeDoacao) {
        this.unidadeDoacao = unidadeDoacao;
    }

    public long getUsuario() {
        return usuario;
    }

    public void setUsuario(long usuario) {
        this.usuario = usuario;
    }

    public String getNomeDonatario() {
        return nomeDonatario;
    }

    public void setNomeDonatario(String nomeDonatario) {
        this.nomeDonatario = nomeDonatario;
    }

    public Date getIdadeDonatario() {
        return idadeDonatario;
    }

    public void setIdadeDonatario(Date idadeDonatario) {
        this.idadeDonatario = idadeDonatario;
    }

    public int getImageCode() {
        return imageCode;
    }

    public void setImageCode(int imageCode) {
        this.imageCode = imageCode;
    }

    public long getDoador() {
        return doador;
    }

    public void setDoador(long doador) {
        this.doador = doador;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(int tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }
}
