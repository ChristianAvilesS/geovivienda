package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.RolUsuario;
import com.geovivienda.geovivienda.entities.ids.RolUsuarioId;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRolUsuarioService {

    List<RolUsuario> listarRolesUsuario();
    RolUsuario buscarRolUsuarioPorId(RolUsuarioId id);

    // Cumple la función de insertar y actualizar
    RolUsuario guardarRolUsuario(RolUsuario rolUsuario);
    void eliminarRolUsuario(RolUsuario rolUsuario);
    List<RolUsuario> buscarRolesPorUsuario(int idUsuario);
    List<String[]> findPredominantUserRol(@Param("id") int idUsuario);
}
