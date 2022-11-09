package com.blood.bloodbackend.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "publicacao")
public class Publicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PUBLICACAO")
    @SequenceGenerator(name = "SEQ_PUBLICACAO", sequenceName = "id_seq_publicacao", allocationSize = 1)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unidade_id")
    private UnidadeDeDoacao unidadeDeDoacao;

    private String nomeDonatario;

    private Date idadeDonatario;

    private int imageCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private long doador;

    private Date dataDeCriacao;

    private int prioridade;

    private  int tipoSanguineo;

    public long getId() {
        return id;
    }



    public UnidadeDeDoacao getUnidadeDeDoacao() {
        return unidadeDeDoacao;
    }

    public void setUnidadeDeDoacao(UnidadeDeDoacao unidadeDeDoacao) {
        this.unidadeDeDoacao = unidadeDeDoacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(Date dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
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

    public long getDoador() {
        return doador;
    }

    public void setDoador(long doador) {
        this.doador = doador;
    }

    public int getImageCode() {
        return imageCode;
    }

    public void setImageCode(int imageCode) {
        this.imageCode = imageCode;
    }
}
