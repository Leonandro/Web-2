package com.blood.bloodbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "usuario_increment", strategy = "increment")
    @JsonProperty
    private long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_papeis",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "papel_id")
    )
    private List<PapelUsuario> papeis;


    @Column(nullable = false)
    private String nome;


    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private int tipoSanguineo;

    private String email;

    @Column(nullable = false)
    private Date dataNascimento;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Publicacao> publicacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.papeis;
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

    public List<Publicacao> getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(List<Publicacao> publicacao) {
        this.publicacao = publicacao;
    }

    public List<PapelUsuario> getPapeis() {
        return papeis;
    }

    public void setPapeis(List<PapelUsuario> papeis) {
        this.papeis = papeis;
    }
}
