package com.blood.bloodbackend.repository;

import com.blood.bloodbackend.model.PapelUsuario;
import com.blood.bloodbackend.model.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

public interface PapelUsuarioRepository extends JpaRepository<PapelUsuario, Long> {

    @Query(value = "SELECT FROM usuario_papeis r WHERE r.name = :name", nativeQuery = true)
    public Optional<PapelUsuario> getByName(@Param(value = "name") String name);

    @Modifying
    @Query(value = "DELETE FROM usuario_papeis AS r WHERE r.usuario_id = :id", nativeQuery = true)
    public void deleteAllByUserId(@Param(value = "id") long id);

}
