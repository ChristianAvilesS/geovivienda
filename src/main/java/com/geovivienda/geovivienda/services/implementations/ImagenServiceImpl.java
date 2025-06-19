package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Imagen;
import com.geovivienda.geovivienda.repositories.IImagenRepository;
import com.geovivienda.geovivienda.services.interfaces.IImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenServiceImpl implements IImagenService {

    @Autowired
    private IImagenRepository repos;

    @Override
    public List<Imagen> listarImagenes() {
        return repos.findAll();
    }

    @Override
    public Imagen buscarImagenPorId(Integer id) {
        return repos.findById(id).orElse(null);
    }

    @Override
    public Imagen guardarImagen(Imagen imagen) {
        return repos.save(imagen);
    }

    @Override
    public void eliminarImagen(Imagen imagen) {
        repos.delete(imagen);
    }

    @Override
    public List<String[]> cantidadImagenesXInmueble() {
        return repos.cantidadImagenesXInmueble();
    }

    @Override
    public List<Imagen> buscarPorIdInmueble(int idInmueble) {
        return repos.buscarPorIdInmueble(idInmueble);
    }
}
