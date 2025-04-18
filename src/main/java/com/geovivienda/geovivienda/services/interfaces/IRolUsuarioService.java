package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.RolUsuario;
import com.geovivienda.geovivienda.entities.ids.RolUsuarioId;

import java.util.List;

public interface IRolUsuarioService {

    public List<RolUsuario> listarRolesUsuario();
    public RolUsuario buscarRolUsuarioPorId(RolUsuarioId id);

    // Cumple la función de insertar y actualizar
    public RolUsuario guardarRolUsuario(RolUsuario rolUsuario);
    public void eliminarRolUsuario(RolUsuario rolUsuario);
    List<RolUsuario> buscarRolesPorUsuario(int idUsuario);
}
