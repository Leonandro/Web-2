package com.jeanlima.springrestapi.service;


import com.jeanlima.springrestapi.exception.BadEntityException;
import com.jeanlima.springrestapi.exception.EntidadeNaoEncontradoException;
import com.jeanlima.springrestapi.model.Cliente;
import com.jeanlima.springrestapi.repository.ClienteRepository;
import com.jeanlima.springrestapi.repository.PedidoRepository;
import com.jeanlima.springrestapi.rest.dto.ClienteAtualizacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;


    public ResponseEntity<Cliente> atualizarCliente (ClienteAtualizacaoDTO cliente) {
        if(cliente.getId() == null) throw new BadEntityException("Não é possível adicionar o cliente pois não foi identificado um ID");

        Optional<Cliente> clienteASerAtualizado = clienteRepository.findById(cliente.getId());

        clienteASerAtualizado.ifPresentOrElse(
                (c) -> {
                    if(cliente.getCpf() != null) clienteASerAtualizado.get().setCpf(cliente.getCpf());
                    if(cliente.getNome() != null) clienteASerAtualizado.get().setNome(cliente.getNome());
                    if(cliente.getPedidos() != null) {
                        clienteASerAtualizado.get().setPedidos( new HashSet<>(pedidoRepository.getPedidosPorIDs(cliente.getPedidos())));
                        pedidoRepository.setPedidosPorIdDeCliente(c.getId(), cliente.getPedidos());
                    }
                    clienteRepository.save(clienteASerAtualizado.get());
                },
                () -> { throw new EntidadeNaoEncontradoException("Cliente não encontrado"); }
        );

        return ResponseEntity.ok(clienteASerAtualizado.get());
    }

}
