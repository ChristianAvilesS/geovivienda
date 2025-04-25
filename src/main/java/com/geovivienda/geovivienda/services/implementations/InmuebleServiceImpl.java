package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.repositories.IInmuebleRepository;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InmuebleServiceImpl implements IInmuebleService {

    @Autowired
    private IInmuebleRepository repos;

    @Override
    public List<Inmueble> listarInmuebles() {
        return repos.findAll();
    }

    @Override
    public Inmueble buscarInmueblePorId(Integer id) {
        return repos.findById(id).orElse(null);
    }

    @Override
    public Inmueble guardarInmueble(Inmueble inmueble) {
        return repos.save(inmueble);
    }

    @Override
    public void eliminarInmueble(Inmueble inmueble) {
        repos.delete(inmueble);
    }

    @Override
    public List<Inmueble> buscarInmueblesEnLugarEnRango(BigDecimal lon, BigDecimal lat, BigDecimal rango) {
        return repos.buscarInmueblesEnLugarEnRango(lon, lat, rango);
    }
}
