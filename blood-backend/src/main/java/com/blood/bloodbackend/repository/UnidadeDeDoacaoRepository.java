package com.blood.bloodbackend.repository;

import com.blood.bloodbackend.model.UnidadeDeDoacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UnidadeDeDoacaoRepository extends JpaRepository<UnidadeDeDoacao, Long> {

    @Modifying
    @Query(value = "DELETE FROM unidade_de_doacao t WHERE t.id = :id", nativeQuery = true)
    void deletarPeloId(@Param("id") Long id);

}
