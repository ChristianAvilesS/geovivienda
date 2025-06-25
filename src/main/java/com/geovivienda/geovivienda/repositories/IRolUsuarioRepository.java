package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.RolUsuario;
import com.geovivienda.geovivienda.entities.ids.RolUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRolUsuarioRepository extends JpaRepository<RolUsuario, RolUsuarioId> {
    @Query("SELECT ur FROM RolUsuario ur WHERE ur.id.idUsuario = :id ORDER BY ur.rol.idRol DESC")
    List<RolUsuario> buscarRolesPorUsuario(@Param("id") int idUsuario);

    @Query("SELECT ru.id.idUsuario, r.rol FROM RolUsuario ru" +
            " INNER JOIN Rol r ON ru.rol = r" +
            " WHERE ru.id.idUsuario= :id" +
            " ORDER BY r.idRol DESC" +
            " LIMIT 1")
    List<String[]> findPredominantUserRol(@Param("id") int idUsuario);

    @Modifying
    @Query(value = "DELETE FROM roles_x_usuario WHERE id_usuario = :idUsuario AND id_rol > :nuevoIdRol", nativeQuery = true)
    void eliminarRolesDeMayorPrioridad(@Param("idUsuario") Integer idUsuario, @Param("nuevoIdRol") Integer nuevoIdRol);

}
