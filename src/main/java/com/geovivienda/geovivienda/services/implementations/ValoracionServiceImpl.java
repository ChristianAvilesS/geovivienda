package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Valoracion;
import com.geovivienda.geovivienda.repositories.IValoracionRepository;
import com.geovivienda.geovivienda.services.interfaces.IValoracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValoracionServiceImpl implements IValoracionService {

    @Autowired
    private IValoracionRepository repos;

    @Override
    public List<Valoracion> listarValoraciones() {
        return repos.findAll();
    }

    @Override
    public Valoracion buscarValoracionPorId(Integer id) {
        return repos.findById(id).orElse(null);
    }

    @Override
    public Valoracion guardarValoracion(Valoracion valoracion) {
        return repos.save(valoracion);
    }

    @Override
    public void eliminarValoracion(Valoracion valoracion) {
        repos.delete(valoracion);
    }
}
