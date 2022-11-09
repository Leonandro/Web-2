package com.blood.bloodbackend.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UsuarioIncomingDTO {

    private long id;

    private String nome;

    private String username;

    private  String password;

    private int tipoSanguineo;

    private String email;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dataNascimento;

    // [Comentado apenas para testes iniciais]
   // private Publicacao publicacao;


    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(int tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

//    public Publicacao getPublicacao() {
//        return publicacao;
//    }
//
//    public void setPublicacao(Publicacao publicacao) {
//        this.publicacao = publicacao;
//    }
}
