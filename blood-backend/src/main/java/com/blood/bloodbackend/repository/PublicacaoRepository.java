package com.blood.bloodbackend.repository;

import com.blood.bloodbackend.model.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {

    @Modifying
    @Query(value = "DELETE FROM publicacao AS r WHERE r.usuario_id = :id", nativeQuery = true)
    public void deleteAllByUserId(@Param(value = "id") long id);


}
