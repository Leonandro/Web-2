package com.jeanlima.springrestapi.service;

import com.jeanlima.springrestapi.exception.BadEntityException;
import com.jeanlima.springrestapi.exception.EntidadeNaoEncontradoException;
import com.jeanlima.springrestapi.model.Produto;
import com.jeanlima.springrestapi.repository.ProdutoRepository;
import com.jeanlima.springrestapi.rest.dto.ProdutoAtualizacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public ResponseEntity<Produto> atualizarProduto (ProdutoAtualizacaoDTO produto) {
        if(produto.getId() == null) throw new BadEntityException("Não é possível adicionar o produto pois não foi identificado um ID");

        Optional<Produto> produtoASerAtualizado = produtoRepository.findById(produto.getId());

        produtoASerAtualizado.ifPresentOrElse(
                (c) -> {
                    if(produto.getPreco() != null) produtoASerAtualizado.get().setPreco(produto.getPreco());
                    if(produto.getDescricao() != null) produtoASerAtualizado.get().setDescricao(produto.getDescricao());

                    produtoRepository.save(produtoASerAtualizado.get());
                },
                () -> { throw new EntidadeNaoEncontradoException("Produto não encontrado"); }
        );

        return ResponseEntity.ok(produtoASerAtualizado.get());
    }
}
