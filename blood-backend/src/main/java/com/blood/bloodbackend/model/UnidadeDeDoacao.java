package com.blood.bloodbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "unidadeDeDoacao")
public class UnidadeDeDoacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_UNIDADE")
    @SequenceGenerator(name = "SEQ_UNIDADE", sequenceName = "id_seq_unidade", allocationSize = 1)
    private long id;

    private String nome;

    @Column(length = 40)
    private String telefones;

    @Column(length = 20)
    private  String horarioAtendimento;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "unidadeDeDoacao")
    private Endereco endereco;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Publicacao> publicacao;

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefones() {
        return telefones;
    }

    public void setTelefones(String telefones) {
        this.telefones = telefones;
    }

    public String getHorarioAtendimento() {
        return horarioAtendimento;
    }

    public void setHorarioAtendimento(String horarioAtendimento) {
        this.horarioAtendimento = horarioAtendimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Publicacao> getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(List<Publicacao> publicacao) {
        this.publicacao = publicacao;
    }
}
