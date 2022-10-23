package com.jeanlima.springrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeanlima.springrestapi.model.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Integer>{

    @Query(value = "SELECT SUM(preco) FROM produto WHERE (id in :ids);", nativeQuery = true)
    Double buscarOValorSomadoDeProdutos(@Param("ids")List<Integer> ids);

    @Query(value = "SELECT preco FROM produto WHERE (id = :id);", nativeQuery = true)
    BigDecimal buscarOValorDeUmProduto(@Param("id") Integer id);
    
}
