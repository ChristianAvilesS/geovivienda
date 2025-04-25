package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Anuncio;

import java.util.List;

public interface IAnuncioService {
    public List<Anuncio> listarAnuncios();
    public Anuncio buscarAnuncioPorId (Integer idAnuncio);

    public Anuncio guardarAnuncio (Anuncio anuncio);
    public void eliminarAnuncio (Anuncio anuncio);

    public List<String[]> cantidadAnunciosXUsuario();
}
