package com.jeanlima.springrestapi.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.jeanlima.springrestapi.enums.StatusPedido;
import com.jeanlima.springrestapi.model.Pedido;
import com.jeanlima.springrestapi.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar( PedidoDTO dto, BigDecimal valorTotal );
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
    public Pedido atualizar(PedidoDTO dto, Integer id);
    
}