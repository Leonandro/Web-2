
package com.jeanlima.springrestapi.rest.controllers;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.jeanlima.springrestapi.exception.EntidadeNaoEncontradoException;
import com.jeanlima.springrestapi.repository.PedidoRepository;
import com.jeanlima.springrestapi.repository.ProdutoRepository;
import com.jeanlima.springrestapi.rest.dto.*;
import com.jeanlima.springrestapi.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.jeanlima.springrestapi.enums.StatusPedido;
import com.jeanlima.springrestapi.model.ItemPedido;
import com.jeanlima.springrestapi.model.Pedido;
import com.jeanlima.springrestapi.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    public String save( @RequestBody PedidoDTO dto ){
        BigDecimal valorToral = new BigDecimal("0");
        if(estoqueService.checarEAtualizarEstoque(dto.getItems())) {

            for(ItemPedidoDTO item : dto.getItems()){
                valorToral = valorToral.add( produtoRepository.buscarOValorDeUmProduto(item.getProduto()).multiply(new BigDecimal(item.getQuantidade())) );
            }

            Pedido pedido = service.salvar(dto, valorToral);
            return "ID: " + pedido.getId().toString() + " - Total: " + valorToral.toString();
        }
        return  "";
    }

    @PutMapping("{id}")
    public String atualizar( @RequestBody PedidoDTO dto, @PathVariable("id") Integer id ){

        Pedido pedido = service.atualizar(dto, id);
        return "ID: " + pedido.getId().toString() + " - Total: " + pedido.getTotal().toString();

    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById( @PathVariable Integer id ){
        return service
                .obterPedidoCompleto(id)
                .map( p -> converter(p) )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
    }

    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map(
                item -> InformacaoItemPedidoDTO
                            .builder()
                            .descricaoProduto(item.getProduto().getDescricao())
                            .precoUnitario(item.getProduto().getPreco())
                            .quantidade(item.getQuantidade())
                            .build()
        ).collect(Collectors.toList());
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id ,
                             @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        service.obterPedidoCompleto(id).ifPresentOrElse(
                (p) -> { repository.deleteById(id);},
                () -> { throw  new EntidadeNaoEncontradoException("Não foi possível encontrar o pedido"); }
        );

    }
}
