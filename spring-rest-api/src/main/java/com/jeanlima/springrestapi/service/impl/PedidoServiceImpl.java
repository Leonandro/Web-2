package com.jeanlima.springrestapi.service.impl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.jeanlima.springrestapi.exception.EntidadeNaoEncontradoException;
import com.jeanlima.springrestapi.repository.*;
import org.springframework.stereotype.Service;

import com.jeanlima.springrestapi.enums.StatusPedido;
import com.jeanlima.springrestapi.exception.PedidoNaoEncontradoException;
import com.jeanlima.springrestapi.exception.RegraNegocioException;
import com.jeanlima.springrestapi.model.Cliente;
import com.jeanlima.springrestapi.model.ItemPedido;
import com.jeanlima.springrestapi.model.Pedido;
import com.jeanlima.springrestapi.model.Produto;
import com.jeanlima.springrestapi.rest.dto.ItemPedidoDTO;
import com.jeanlima.springrestapi.rest.dto.PedidoDTO;
import com.jeanlima.springrestapi.service.PedidoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    
    private final PedidoRepository repository;

    private final EstoqueRepository estoqueRepository;
    private final ClienteRepository clientesRepository;
    private final ProdutoRepository produtosRepository;
    private final ItemPedidoRepository itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto, BigDecimal valorTotal) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(valorTotal);
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }
    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

    }
    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        
        return repository.findByIdFetchItens(id);
    }
    @Override
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException() );
        
    }


    @Override
    @Transactional
    public Pedido atualizar(PedidoDTO dto, Integer id) {
        Optional<Pedido> pedidoASerAtualizado = repository.findById(id);
        BigDecimal valorToral = new BigDecimal("0");
        if(pedidoASerAtualizado.isPresent()) {
            for (ItemPedido item : pedidoASerAtualizado.get().getItens()) {

                //Devolve ao estoque os produtos retirados anteriormente
                //System.out.println("Item: " + item.getProduto());
                estoqueRepository.atualizarPositivamenteQuantidadeNoEstoquePorIdDoProduto(item.getProduto().getId(), item.getQuantidade());
            }

            for (ItemPedidoDTO item : dto.getItems()) {
                valorToral = valorToral.add(produtosRepository.buscarOValorDeUmProduto(item.getProduto()).multiply(new BigDecimal(item.getQuantidade())));

                //Remove do estoque os itens novos
                estoqueRepository.atualizarQuantidadeNoEstoquePorIdDoProduto(item.getProduto(), item.getQuantidade());
            }
        } else {
            throw new EntidadeNaoEncontradoException("Pedido não encontrado");
        }

        pedidoASerAtualizado.get().setItens(null);

        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));


        pedidoASerAtualizado.get().setTotal(valorToral);
        pedidoASerAtualizado.get().setDataPedido(LocalDate.now());
        pedidoASerAtualizado.get().setCliente(cliente);
        pedidoASerAtualizado.get().setStatus(StatusPedido.REALIZADO);

        itemsPedidoRepository.deleteAllByPedidoId(pedidoASerAtualizado.get().getId());

        List<ItemPedido> itemsPedido = converterItems(pedidoASerAtualizado.get(), dto.getItems());
        repository.save(pedidoASerAtualizado.get());
        itemsPedidoRepository.saveAll(itemsPedido);
        pedidoASerAtualizado.get().setItens(itemsPedido);
        return pedidoASerAtualizado.get();
    }
}
