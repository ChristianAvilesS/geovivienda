package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.PK_RolUsuario;
import com.geovivienda.geovivienda.entities.RolUsuario;

import java.util.List;

public interface IRolUsuarioService {

    public List<RolUsuario> listarRolUsuarioes();
    public RolUsuario buscarRolUsuarioPorId(PK_RolUsuario id);

    // Cumple la función de insertar y actualizar
    public RolUsuario guardarRolUsuario(RolUsuario rolUsuario);
    public void eliminarRolUsuario(RolUsuario rolUsuario);
}
