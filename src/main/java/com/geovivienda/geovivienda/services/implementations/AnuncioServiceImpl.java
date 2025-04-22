package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Anuncio;
import com.geovivienda.geovivienda.repositories.IAnuncioRepository;
import com.geovivienda.geovivienda.services.interfaces.IAnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnuncioServiceImpl implements IAnuncioService {

    @Autowired
    private IAnuncioRepository repos;

    @Override
    public List<Anuncio> listarAnuncios() {
        return repos.findAll();
    }

    @Override
    public Anuncio buscarAnuncioPorId(Integer id) {
        return repos.findById(id).orElse(null);
    }

    @Override
    public Anuncio guardarAnuncio(Anuncio anuncio) {
        return repos.save(anuncio);
    }

    @Override
    public void eliminarAnuncio(Anuncio anuncio) {
        repos.delete(anuncio);
    }
}
