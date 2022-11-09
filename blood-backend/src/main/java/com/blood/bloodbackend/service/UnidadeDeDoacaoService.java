package com.blood.bloodbackend.service;

import com.blood.bloodbackend.model.DTO.ErrorResponseDTO;
import com.blood.bloodbackend.model.DTO.UnidadeDoacaoIncomingDTO;
import com.blood.bloodbackend.model.Endereco;
import com.blood.bloodbackend.model.UnidadeDeDoacao;
import com.blood.bloodbackend.repository.EnderecoRepository;
import com.blood.bloodbackend.repository.UnidadeDeDoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UnidadeDeDoacaoService {

    @Autowired
    UnidadeDeDoacaoRepository repository;

    @Autowired
    EnderecoRepository enderecoRepository;

    public List<UnidadeDeDoacao> getUnidades () {
        return repository.findAll();
    }

    public ResponseEntity<?> getUnidade (long id) {
        Optional<UnidadeDeDoacao> unidade = repository.findById(id);

        if(!unidade.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("Não foi possível identificar a unidade com o id " + id + " fornecido"));
        }

        return ResponseEntity.ok(unidade.get());
    }

    public ResponseEntity<?> cadastrarUnidade (UnidadeDoacaoIncomingDTO unidade) {
        Optional<Endereco> endereco = enderecoRepository.findById(unidade.getEndereco());
        if (!endereco.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("Não foi possível identificar o endereço da unidade pelo id " + unidade.getEndereco() + " fornecido"));
        }
        UnidadeDeDoacao novaUnidade = new UnidadeDeDoacao();
        novaUnidade.setNome(unidade.getNome());
        novaUnidade.setHorarioAtendimento(unidade.getHorarioAtendimento());
        novaUnidade.setTelefones(unidade.getTelefones());
        endereco.get().setUnidadeDeDoacao(novaUnidade);
        UnidadeDeDoacao unidadeASerRetornada = repository.save(novaUnidade);
        return ResponseEntity.ok(unidadeASerRetornada);
    }

    @Transactional
    public ResponseEntity<?> deletarUnidade (Long id) {
        Optional<UnidadeDeDoacao> unidade = repository.findById(id);

        if (!unidade.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("Não foi possível identificar a unidade pelo id " + id + " fornecido"));
        }

        enderecoRepository.deleteById(unidade.get().getEndereco().getId());

        repository.deletarPeloId(id);
        return ResponseEntity.ok().build();
    }

}
