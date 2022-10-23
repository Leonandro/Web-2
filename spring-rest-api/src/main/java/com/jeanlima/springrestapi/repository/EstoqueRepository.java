package com.jeanlima.springrestapi.repository;

import com.jeanlima.springrestapi.model.Cliente;
import com.jeanlima.springrestapi.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque,Integer> {

    @Query(value = "SELECT e.* FROM estoque AS e, produto AS p WHERE (e.produto_id = p.id) AND p.descricao LIKE %:nome% ", nativeQuery = true)
    public List<Estoque> buscarEstoquePorNomeDoProduto(@Param("nome") String nome);

    @Query(value = "SELECT e.quantidade FROM estoque AS e WHERE (e.produto_id = :id) ", nativeQuery = true)
    public Optional<Integer> buscarQuantidadeNoEstoquePorIdDoProduto(@Param("id") Integer id);

    @Modifying
    @Query(value = "UPDATE estoque SET quantidade = (quantidade - :delta) WHERE (produto_id = :id);  ", nativeQuery = true)
    void atualizarQuantidadeNoEstoquePorIdDoProduto(@Param("id") Integer id, @Param("delta") Integer delta);

    @Modifying
    @Query(value = "UPDATE estoque SET quantidade = (quantidade + :delta) WHERE (produto_id = :id);  ", nativeQuery = true)
    void atualizarPositivamenteQuantidadeNoEstoquePorIdDoProduto(@Param("id") Integer id, @Param("delta") Integer delta);
}
