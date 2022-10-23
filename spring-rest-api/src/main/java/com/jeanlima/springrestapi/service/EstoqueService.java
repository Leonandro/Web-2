package com.jeanlima.springrestapi.service;


import com.jeanlima.springrestapi.exception.EntidadeNaoEncontradoException;
import com.jeanlima.springrestapi.exception.RegraNegocioException;
import com.jeanlima.springrestapi.model.Estoque;
import com.jeanlima.springrestapi.repository.EstoqueRepository;
import com.jeanlima.springrestapi.rest.dto.ItemPedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {

    @Autowired
    EstoqueRepository estoqueRepository;

    @Transactional
    public boolean checarEAtualizarEstoque (List<ItemPedidoDTO> items) {
        int retorno = -1;
        for (ItemPedidoDTO item : items) {
            if(item.getQuantidade() < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O pedido de id " + item.getProduto() +
                                                                                                   " veio com quantidade negativa");

            estoqueRepository.buscarQuantidadeNoEstoquePorIdDoProduto(item.getProduto()).ifPresentOrElse(
                    (i) -> { if(i < item.getQuantidade()) throw  new RegraNegocioException("O estoque do produto " + item.getProduto() +
                                                                                           " não é suficiente para o solicitado no pedido");},
                    () -> { throw new EntidadeNaoEncontradoException("O produto de ID " + item.getProduto() + " não foi encontrado");
                    }
            );
        }

        for (ItemPedidoDTO item : items) {
            estoqueRepository.atualizarQuantidadeNoEstoquePorIdDoProduto(item.getProduto(), item.getQuantidade());
        }

        return true;
    }

}
