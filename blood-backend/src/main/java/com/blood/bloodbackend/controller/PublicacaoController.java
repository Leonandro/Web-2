package com.blood.bloodbackend.controller;

import com.blood.bloodbackend.model.DTO.PublicacaoIncomingDTO;
import com.blood.bloodbackend.model.DTO.UnidadeDoacaoIncomingDTO;
import com.blood.bloodbackend.model.Publicacao;
import com.blood.bloodbackend.model.UnidadeDeDoacao;
import com.blood.bloodbackend.service.PublicacaoService;
import com.blood.bloodbackend.service.UnidadeDeDoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicacao")
public class PublicacaoController {

    @Autowired
    PublicacaoService service;

    @GetMapping
    public List<Publicacao> getPublicacoes () {
        return service.getPublicacoes();
    }

    @GetMapping(path = "/{id}")
    public  Publicacao getPublicacao (@PathVariable("id") long id) {
        return service.getPublicacao(id);
    }

    @PostMapping
    public Publicacao postPublicacao (@RequestBody PublicacaoIncomingDTO publicacao) {
        return service.cadastrarPublicacao(publicacao);
    }

    @PutMapping
    public Publicacao setDoador (@RequestParam (required = true) long publicacao, @RequestParam (required = true) long doador) {
        return service.definirDoador(publicacao, doador);
    }

    @DeleteMapping(path = "/{id}")
    public Publicacao deleePublicacao (@PathVariable("id") long id) {
        return  service.deletePublicacao(id);
    }
}
