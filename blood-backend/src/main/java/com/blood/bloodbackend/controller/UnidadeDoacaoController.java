package com.blood.bloodbackend.controller;

import com.blood.bloodbackend.model.DTO.UnidadeDoacaoIncomingDTO;
import com.blood.bloodbackend.model.UnidadeDeDoacao;
import com.blood.bloodbackend.service.UnidadeDeDoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidadedoacao")
public class UnidadeDoacaoController {

    @Autowired
    UnidadeDeDoacaoService service;

    @GetMapping
    public List<UnidadeDeDoacao> getUnidades () {
        return service.getUnidades();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUnidade (@PathVariable("id") long id) {
        return service.getUnidade(id);
    }

    @PostMapping
    public ResponseEntity<?> postUnidade (@RequestBody UnidadeDoacaoIncomingDTO unidade) {
        return service.cadastrarUnidade(unidade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUnidadeDeDoacao(@PathVariable("id") long id) {
        return this.service.deletarUnidade(id);
    }
}
