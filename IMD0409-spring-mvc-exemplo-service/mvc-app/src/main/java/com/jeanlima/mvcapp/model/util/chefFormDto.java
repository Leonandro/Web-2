package com.jeanlima.mvcapp.model.util;

import com.jeanlima.mvcapp.model.Receita;
import com.jeanlima.mvcapp.model.Usuario;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

public class chefFormDto {

    private Integer id;

    private String alcunha;

    private String restaurante;

    private int usuario;

    private List<Integer> receitas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlcunha() {
        return alcunha;
    }

    public void setAlcunha(String alcunha) {
        this.alcunha = alcunha;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public List<Integer> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Integer> receitas) {
        this.receitas = receitas;
    }
}
