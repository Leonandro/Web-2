package com.jeanlima.springrestapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jeanlima.springrestapi.model.Cliente;
import com.jeanlima.springrestapi.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Integer>{

    List<Pedido> findByCliente(Cliente cliente);
    //Optional<Pedido> findByIdFetchItens(Integer id);
    @Query(" select p from Pedido p left join fetch p.itens where p.id = :id ")
    Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);

    @Query(value = " select * from pedido where id in :ids ", nativeQuery = true)
    List<Pedido> getPedidosPorIDs(@Param("ids") List<Integer> ids);

    @Query(value = " UPDATE pedido where id in :idpedidos SET cliente_id = :idcliente", nativeQuery = true)
    void setPedidosPorIdDeCliente(@Param("idcliente") Integer id, @Param("idpedidos") List<Integer> ids);

    @Query(value = "SELECT p.* FROM pedido AS p WHERE (p.cliente_id = :id)", nativeQuery = true)
    List<Pedido> buscarTodosOsPedidosDeUmCliente(@Param("id") Integer id);
}
