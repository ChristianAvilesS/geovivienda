package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Rol;

import java.util.List;

public interface IRolService {

    public List<Rol> listarRoles();
    public Rol buscarRolPorId(Integer id);

    // Cumple la función de insertar y actualizar
    public Rol guardarRol(Rol rol);
    public void eliminarRol(Rol rol);

}
