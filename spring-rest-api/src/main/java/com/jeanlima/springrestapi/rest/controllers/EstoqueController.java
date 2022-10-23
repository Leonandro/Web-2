package com.jeanlima.springrestapi.rest.controllers;

import com.jeanlima.springrestapi.exception.BadEntityException;
import com.jeanlima.springrestapi.exception.EntidadeNaoEncontradoException;
import com.jeanlima.springrestapi.model.Estoque;
import com.jeanlima.springrestapi.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    @Autowired
    EstoqueRepository estoqueRepository;

    @GetMapping
    public List<Estoque> buscarTodosOsItemsDoEStoque () {
        return estoqueRepository.findAll();
    }

    @GetMapping("/nome")
    public List<Estoque> buscarEstoquePorNomeDoProduto (@RequestParam("nome") String produto) {

        return estoqueRepository.buscarEstoquePorNomeDoProduto(produto);
    }

    @PostMapping
    public ResponseEntity<Estoque> adicionarItemAoEstoque (@RequestBody Estoque estoque) {
        return ResponseEntity.ok(estoqueRepository.save(estoque));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionarProdutosAoEstoque(@RequestParam("id") Integer id, @RequestParam("qtd") Integer quantidade) {
        Optional<Estoque> estoqueASerModificado = estoqueRepository.findById(id);

        estoqueASerModificado.ifPresentOrElse(
                (e) -> {
                    estoqueASerModificado.get().setQuantidade( e.getQuantidade() + quantidade );
                    estoqueRepository.save(estoqueASerModificado.get());
                },
                () -> {
                    throw new EntidadeNaoEncontradoException("Não foi possível achar um estoque para o ID fornecido");
                }
        );
    }


}
