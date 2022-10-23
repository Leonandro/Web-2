package com.jeanlima.springrestapi.rest.controllers;

import java.util.List;
import java.util.Optional;

import ch.qos.logback.core.net.server.Client;
import com.jeanlima.springrestapi.model.Pedido;
import com.jeanlima.springrestapi.repository.PedidoRepository;
import com.jeanlima.springrestapi.rest.dto.ClienteAtualizacaoDTO;
import com.jeanlima.springrestapi.rest.dto.ClientePedidosDTO;
import com.jeanlima.springrestapi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.jeanlima.springrestapi.model.Cliente;
import com.jeanlima.springrestapi.repository.ClienteRepository;

@RequestMapping("/api/clientes")
@RestController //anotação especializadas de controller - todos já anotados com response body!
public class ClienteController {

    @Autowired
    private ClienteRepository clientes;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("{id}")
    public Cliente getClienteById( @PathVariable Integer id ){
        return clientes
                .findById(id)
                .orElseThrow(() -> //se nao achar lança o erro!
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado"));
    }

    @GetMapping("/pedidos/{id}")
    public ClientePedidosDTO getPedidosDoClienteById( @PathVariable("id") Integer id ){
        Optional<Cliente> cliente = clientes.findById(id);

        cliente.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Cliente não encontrado"));

        ClientePedidosDTO clienteASerRetornado = new ClientePedidosDTO();
        clienteASerRetornado.setCpf(cliente.get().getCpf());
        clienteASerRetornado.setNome(cliente.get().getNome());
        clienteASerRetornado.setId(cliente.get().getId());
        clienteASerRetornado.setPedidos(pedidoRepository.buscarTodosOsPedidosDeUmCliente(cliente.get().getId()));

        List<Pedido> a = pedidoRepository.buscarTodosOsPedidosDeUmCliente(cliente.get().getId());

        return clienteASerRetornado;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save( @RequestBody Cliente cliente ){
        return clientes.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        clientes.findById(id)
                .map( cliente -> {
                    clientes.delete(cliente );
                    return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado") );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id,
                        @RequestBody Cliente cliente ){
        clientes
                .findById(id)
                .map( clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);
                    return clienteExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Cliente não encontrado") );
    }

    @GetMapping
    public List<Cliente> find( Cliente filtro ){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(
                                            ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(filtro, matcher);
        return clientes.findAll(example);
    }

    @PatchMapping
    public ResponseEntity<Cliente> atualizarCliente (@RequestBody ClienteAtualizacaoDTO cliente) {
            return clienteService.atualizarCliente(cliente);
    }

}
