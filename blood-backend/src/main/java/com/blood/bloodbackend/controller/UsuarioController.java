package com.blood.bloodbackend.controller;


import com.blood.bloodbackend.model.DTO.UsuarioIncomingDTO;
import com.blood.bloodbackend.model.Usuario;
import com.blood.bloodbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<?> getUsuarios () {
       return usuarioService.getUsuarios();
    }

    @GetMapping(path = "/{id}")
    public  ResponseEntity<?> getUsuario (@PathVariable("id") long id) {
        return usuarioService.getUsuario(id);
    }

    @GetMapping(path = "/byusername")
    public  ResponseEntity<?> getUsuarioPeloUsername(@RequestParam (required = true) String username) {
       return usuarioService.getUsuarioByUsername(username);
    }

    @PostMapping
    public ResponseEntity<?> cadastrarUsuario (@RequestBody UsuarioIncomingDTO usuario, @RequestHeader(value = "ClientID", required = false) String clientId) {

        if(clientId == null || !clientId.equals("bloodapp_98")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return usuarioService.cadastrarUsuario(usuario);
    }

    @PatchMapping(path = "/{id}")
    public  ResponseEntity<?> definirPapeis(@PathVariable("id") Long id, @RequestParam (required = true) List<String> papeis) {
        return usuarioService.defenirPapeis(id,papeis);
    }


//    @DeleteMapping(path = "/{id}")
//    public ResponseEntity<?> deletarUsuario (@PathVariable("id") long id) {
//        System.out.println("Chegou no controler");
//
//        return  usuarioService.deleteUsuario(id);
//    }

}
