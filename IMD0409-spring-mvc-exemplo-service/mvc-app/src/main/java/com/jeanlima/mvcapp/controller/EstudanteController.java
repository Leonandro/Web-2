package com.jeanlima.mvcapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeanlima.mvcapp.model.Estudante;
import com.jeanlima.mvcapp.service.EstudanteService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/estudante")
public class EstudanteController {


    @Autowired
    @Qualifier("estudanteServiceImpl")
    EstudanteService estudanteService;

    @RequestMapping("/showForm")
    public String showFormEstudante(Model model){

        model.addAttribute("estudante", new Estudante());
        return "estudante/formEstudante";
    }

    @RequestMapping("/addEstudante")
    public String showFormEstudante(@ModelAttribute("estudante") Estudante estudante,  Model model){

        if(estudante.getPrimeiroNome() != null && estudante.getUltimoNome() != null) estudanteService.salvarEstudante(estudante);
        model.addAttribute("estudante", estudante);
        return "estudante/paginaEstudante";
    }

    @RequestMapping("/detalhesEstudante")
    public String showDetailsEstudante(@RequestParam("id") String id, Model model){
        Optional<Estudante> estudante = Optional.ofNullable(estudanteService.getEstudanteById(Integer.parseInt(id)));

        estudante.ifPresentOrElse(
                est -> {model.addAttribute("estudante", est);},
                () -> {model.addAttribute("estudante", new Estudante());}
        );
        return "estudante/detalhesEstudante";
    }

    @RequestMapping("/getListaEstudantes")
    public String showListaEstudante(Model model){

        List<Estudante> estudantes = estudanteService.getListaEstudante();
        model.addAttribute("estudantes",estudantes);
        return "estudante/listaEstudantes";

    }

    @RequestMapping("/getListaEstudantesPorCurso")
    public String showListaEstudantesPorCurso(@RequestParam("curso") String curso, Model model){

        List<Estudante> estudantes = estudanteService.getEstudantesPorCurso(curso);
        model.addAttribute("estudantes",estudantes);
        return "estudante/listaEstudantesPorCurso";

    }

    @RequestMapping("/getListaEstudantesPorLinguagem")
    public String showListaEstudantesPorLinguagem(@RequestParam("lang") String lang, Model model){

        List<Estudante> estudantes = estudanteService.getEstudantesPorLinguagem(lang);
        model.addAttribute("estudantes",estudantes);
        return "estudante/listaEstudantesPorLinguagem";

    }

    @RequestMapping("/deletarEstudante")
    public String deletarEstudante(@RequestParam("id") String id, Model model){
        Optional<Estudante> estudante = Optional.ofNullable(estudanteService.getEstudanteById(Integer.parseInt(id)));

        estudante.ifPresentOrElse(
                est -> {this.estudanteService.deletarEstudante(est);},
                () -> {}
        );

        List<Estudante> estudantes = estudanteService.getListaEstudante();
        model.addAttribute("estudantes",estudantes);
        return "estudante/listaEstudantes";
    }

    
    
}
