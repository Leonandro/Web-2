package com.blood.bloodbackend.repository;

import com.blood.bloodbackend.model.PapelUsuario;
import com.blood.bloodbackend.model.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PapelUsuarioRepository extends JpaRepository<PapelUsuario, Long> {

    @Query(value = "SELECT FROM roles r WHERE r.name = :name", nativeQuery = true)
    public Optional<PapelUsuario> getByName(@Param(value = "name") String name);

}
