package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.RolUsuario;
import com.geovivienda.geovivienda.entities.ids.RolUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRolUsuarioRepository extends JpaRepository<RolUsuario, RolUsuarioId> {
    @Query("SELECT ur FROM RolUsuario ur WHERE ur.id.idRol = :id")
    List<RolUsuario> buscarRolesPorUsuario(@Param("id") int idUsuario);
}
