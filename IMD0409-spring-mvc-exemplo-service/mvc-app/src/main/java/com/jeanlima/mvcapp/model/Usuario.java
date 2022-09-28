package com.jeanlima.mvcapp.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario extends AbstractModel<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    @SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "id_seq_usuario", allocationSize = 1)
    private Integer id;

    private String nome;

    private int idade;

    private String email;

    @OneToOne(mappedBy = "usuario")
    private Chef chef;

    @OneToMany(mappedBy = "usuario")
    private List<Avaliacao> avaliacoes;

    @ManyToMany(mappedBy = "usuarioList")
    private List<Receita> receitasFavoritas;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Receita> getReceitasFavoritas() {
        return receitasFavoritas;
    }

    public void setReceitasFavoritas(List<Receita> receitasFavoritas) {
        this.receitasFavoritas = receitasFavoritas;
    }

    @Override
    public String getNomeDaClasse() {
        return "usuario";
    }
}
