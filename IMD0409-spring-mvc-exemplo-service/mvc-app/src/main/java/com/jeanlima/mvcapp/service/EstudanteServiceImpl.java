package com.jeanlima.mvcapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jeanlima.mvcapp.model.Curso;
import org.springframework.stereotype.Component;

import com.jeanlima.mvcapp.model.Estudante;

@Component
public class EstudanteServiceImpl implements EstudanteService{
    public List<Estudante> estudantes = new ArrayList<Estudante>();
    public List<Curso> cursos = new ArrayList<Curso>();

    @Override
    public void salvarEstudante(Estudante estudante) {
        System.out.println(estudante.toString());
        try{
            this.estudantes.add(estudante);
        } catch(Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
        }
        
        
    }

    @Override
    public void deletarEstudante(Estudante estudante) {

        this.estudantes.remove(estudante);
        
    }

    @Override
    public Estudante getEstudanteById(Integer id) {
        for (Estudante estudante : estudantes) {
            if(estudante.getId() == id){
                return estudante;
            }
        }
        return null;
    }

    @Override
    public List<Estudante> getListaEstudante() {
        return this.estudantes;
    }

    @Override
    public List<Estudante> getEstudantesPorCurso(String curso) {
        List<Estudante> estudantesDoCurso = new ArrayList<>();
        for (Estudante e : estudantes) {
            if(e.getCurso().equalsIgnoreCase(curso)) estudantesDoCurso.add(e);
        }

        return estudantesDoCurso;
    }

    @Override
    public  List<Estudante> getEstudantesPorLinguagem (String linguagem) {
        List<Estudante> estudantesDaLinguagem = new ArrayList<>();
        for (Estudante e : estudantes) {
            if(e.getLinguagem().equalsIgnoreCase(linguagem)) estudantesDaLinguagem.add(e);
        }

        return estudantesDaLinguagem;
    }

    public List<String> getAllCursos () {
        return this.cursos.stream().map(c -> c.getNome()).collect(Collectors.toList());
    }

    public void addCurso (String curso) {
        this.cursos.add(new Curso(curso));
    }

    
}
