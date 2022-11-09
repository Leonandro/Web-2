package com.blood.bloodbackend.service;

import com.blood.bloodbackend.model.PapelUsuario;
import com.blood.bloodbackend.repository.PapelUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PapeisService {

    @Autowired
    PapelUsuarioRepository repository;

    public List<PapelUsuario> getAll () {
        return repository.findAll();
    }

    public ResponseEntity<?> postPapel (String papel) {
        PapelUsuario p = new PapelUsuario();
        p.setId(repository.findAll().size() + 1);
        p.setName(papel);
        return ResponseEntity.ok(repository.save(p));
    }
}
