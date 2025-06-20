package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Direccion;
import java.util.List;

public interface IDireccionService {

    List<Direccion> listarDirecciones();
    Direccion buscarDireccionPorId(Integer id);

    // Cumple la función de insertar y actualizar
    Direccion guardarDireccion(Direccion direccion);
    void eliminarDireccion(Direccion direccion);
}
