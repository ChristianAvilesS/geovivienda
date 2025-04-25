package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Imagen;

import java.util.List;

public interface IImagenService {
    public List<Imagen> listarImagenes();
    public Imagen buscarImagenPorId(Integer id);

    public Imagen guardarImagen(Imagen imagen);
    public void eliminarImagen(Imagen imagen);

    public List<String[]> cantidadImagenesXInmueble();

}
