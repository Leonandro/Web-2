package com.jeanlima.springrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeanlima.springrestapi.model.ItemPedido;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido,Integer>{


    @Modifying
    @Query(value = "DELETE FROM item_pedido AS ip WHERE ip.pedido_id = :id", nativeQuery = true)
    void deleteAllByPedidoId(@Param("id") Integer id);
}
