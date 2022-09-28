package com.jeanlima.mvcapp.model;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "receita")
public class Receita extends AbstractModel<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECEITA")
    @SequenceGenerator(name = "SEQ_RECEITA", sequenceName = "id_seq_receita", allocationSize = 1)
    private Integer id;

    private String nome;

    private String ingredientes;

    @Column(columnDefinition = "TEXT")
    private String preparo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chef_id")
    private Chef chef;

    @OneToMany(mappedBy = "receita")
    private List<Avaliacao> avaliacoes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "receitas_usuarios",
            joinColumns = @JoinColumn(name = "receita_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuarioList;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getNomeDaClasse() {
        return "receita";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getPreparo() {
        return preparo;
    }

    public void setPreparo(String preparo) {
        this.preparo = preparo;
    }

    public Chef getChef() {
        return chef;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }
}

