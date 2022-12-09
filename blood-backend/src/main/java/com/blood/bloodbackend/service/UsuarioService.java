package com.blood.bloodbackend.service;

import com.blood.bloodbackend.model.DTO.ErrorResponseDTO;
import com.blood.bloodbackend.model.DTO.UsuarioIncomingDTO;
import com.blood.bloodbackend.model.PapelUsuario;
import com.blood.bloodbackend.model.Usuario;
import com.blood.bloodbackend.repository.PapelUsuarioRepository;
import com.blood.bloodbackend.repository.PublicacaoRepository;
import com.blood.bloodbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PapelUsuarioRepository papelUsuarioRepository;

    @Autowired
    PublicacaoRepository publicacaoRepository;

    public  ResponseEntity<?> getUsuarios () {

        try {
            return ResponseEntity.ok(usuarioRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO("Ocorreu um erro na hora de buscar os usuários no banco", e.getMessage()));
        }

    }

    public ResponseEntity<?> getUsuario (long id) {
        try {
            Optional <Usuario> usuario = usuarioRepository.findById(id);
            if(!usuario.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("Não foi possível identificar o usuário com o id " + id + " fornecido"));
            }
            return ResponseEntity.ok(usuario.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO("Ocorreu um erro na hora de buscar o usuário no banco", e.getMessage()));

        }
    }

    public ResponseEntity<Usuario> getUsuarioByUsername (String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if(usuario == null){
            throw  new NoSuchElementException("Usuário não encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    @Transactional
    public ResponseEntity<?> deleteUsuario (long id) {
        Optional <Usuario> usuario = usuarioRepository.findById(id);
        if(!usuario.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("Não foi possível identificar o usuário com o id " + id + " fornecido"));
        }
        System.out.println("DELETANDO PAE");
        papelUsuarioRepository.deleteAllByUserId(id);
        publicacaoRepository.deleteAllByUserId(id);
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok(usuario.get());
    }

    public ResponseEntity<Usuario> cadastrarUsuario (@RequestBody UsuarioIncomingDTO usuario) {
        Usuario usuarioASerCadastrado = new Usuario();

        usuarioASerCadastrado.setNome(usuario.getNome());
        usuarioASerCadastrado.setUsername(usuario.getUsername());
        usuarioASerCadastrado.setPassword(usuario.getPassword());
        usuarioASerCadastrado.setDataNascimento(usuario.getDataNascimento());
        usuarioASerCadastrado.setEmail(usuario.getEmail());
        usuarioASerCadastrado.setTipoSanguineo(usuario.getTipoSanguineo());

        return ResponseEntity.ok(usuarioRepository.save(usuarioASerCadastrado));
    }

    public ResponseEntity<?> defenirPapeis(Long idUsuario, List<String> papeis) {
        List<PapelUsuario> novosPapeis = new ArrayList<>();
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if(!usuario.isPresent()){
            throw  new NoSuchElementException("Usuário não encontrado");
        }

        for (String papel: papeis) {
            Optional<PapelUsuario> p = papelUsuarioRepository.getByName(papel.toUpperCase());
            if(p.isPresent()) {
                novosPapeis.add(p.get());
            }
        }

        usuario.get().setPapeis(novosPapeis);
        usuarioRepository.save(usuario.get());
        return ResponseEntity.ok().build();
    }


}
