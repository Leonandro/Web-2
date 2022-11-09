package com.blood.bloodbackend.controller;

import com.blood.bloodbackend.model.DTO.UsuarioIncomingDTO;
import com.blood.bloodbackend.service.PapeisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("papel")
public class PapeisController {

    @Autowired
    PapeisService service;

    @GetMapping
    public ResponseEntity<?> getPapeis () {
        return ResponseEntity.ok(service.getAll());
    }


    @GetMapping(path = "/name")
    public  ResponseEntity<?> getPapelPeloNome(@RequestParam(required = true) String name) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarPapel (@RequestParam(required = true) String papel) {
        return service.postPapel(papel);
    }
}
