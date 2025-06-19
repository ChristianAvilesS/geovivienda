package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Rol;

import java.util.List;

public interface IRolService {

    List<Rol> listarRoles();
    Rol buscarRolPorId(Integer id);

    // Cumple la función de insertar y actualizar
    Rol insertarRol(Rol rol);
    Rol guardarRol(Rol rol);
    void eliminarRol(Rol rol);
    Rol buscarRolPorNombre(String nombre);

}
