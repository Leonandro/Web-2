package com.blood.bloodbackend.repository;

import com.blood.bloodbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

     Usuario findByUsername(String username);

//     @Modifying
//     @Query(value = "", nativeQuery = true)
//     void deletarUsuario(@Param("id") Integer id);
}
