package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE u.username = :username AND u.password = :password")
    Usuario buscarUsuarioPorUsernameYPassword(@Param("username") String username, @Param("password") String password);
}
