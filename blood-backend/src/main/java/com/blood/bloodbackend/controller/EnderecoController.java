package com.blood.bloodbackend.controller;

import com.blood.bloodbackend.model.DTO.EnderecoIncomingDTO;
import com.blood.bloodbackend.model.Endereco;
import com.blood.bloodbackend.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<?> getEndereços () {
        return  enderecoService.getEnderecos();
    }


    @PostMapping
    public  ResponseEntity<?> cadastrarEnderco (@RequestBody EnderecoIncomingDTO endereco) {
        return this.enderecoService.cadastrar(endereco);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deletarEndereco (@PathVariable("id") long id) {
        return  this.enderecoService.deletarEndereco(id);
    }


}
