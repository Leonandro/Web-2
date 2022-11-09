package com.blood.bloodbackend.service;

import com.blood.bloodbackend.model.DTO.EnderecoIncomingDTO;
import com.blood.bloodbackend.model.DTO.ErrorResponseDTO;
import com.blood.bloodbackend.model.Endereco;
import com.blood.bloodbackend.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    //TODO cadastrar a unidade de doação referente (quando a parte de unidade de doação estiver pronta)
    public ResponseEntity<Endereco> cadastrar (EnderecoIncomingDTO enderecoASerCadastradoDTO) {
        Endereco enderecoASerCadastrado = new Endereco();

        enderecoASerCadastrado.setLogradouro(enderecoASerCadastradoDTO.getLogradouro());
        enderecoASerCadastrado.setLatitude(enderecoASerCadastradoDTO.getLatitude());
        enderecoASerCadastrado.setLongitude(enderecoASerCadastradoDTO.getLongitude());

        return ResponseEntity.ok(enderecoRepository.save(enderecoASerCadastrado));

    }

    public ResponseEntity<?> getEnderecos () {

        try {
            return  ResponseEntity.ok(enderecoRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO("Houve algum problema na hora de buscar os endereços no banco", e.getMessage()));
        }
    }

    public ResponseEntity<?> deletarEndereco(long id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        if(endereco.isPresent()){
            try {
                enderecoRepository.deleteById(id);
                return  ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO("Houve algum problema na hora de deletar o endereço no banco", e.getMessage()));
            }
        }

        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("Não foi possível encontrar o endereço pelo id fornecido"));
    }
}
