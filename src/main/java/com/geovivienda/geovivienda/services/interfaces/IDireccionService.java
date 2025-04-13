package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Direccion;

import java.util.List;

public interface IDireccionService {

    public List<Direccion> listarDirecciones();
    public Direccion buscarDireccionPorId(Integer id);

    // Cumple la función de insertar y actualizar
    public Direccion guardarDireccion(Direccion direccion);
    public void eliminarDireccion(Direccion direccion);
}
