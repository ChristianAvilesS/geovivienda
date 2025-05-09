package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.repositories.IInmuebleRepository;
import com.geovivienda.geovivienda.repositories.IInmuebleUsuarioRepository;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InmuebleServiceImpl implements IInmuebleService {

    @Autowired
    private IInmuebleRepository repos;

    @Autowired
    private IInmuebleUsuarioRepository inmuebleUsuarioRepos;

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
    public Inmueble editarInmueble(Inmueble inmueble) {
        return repos.save(inmueble);
    }

    @Override
    public void eliminarInmueble(Inmueble inmueble) {
        repos.deleteLogically(inmueble.getIdInmueble());
        inmuebleUsuarioRepos.deleteInmueblesUsuarioByInmueble(inmueble.getIdInmueble());
    }

    @Override
    public List<Inmueble> buscarInmueblesEnLugarEnRango(BigDecimal lon, BigDecimal lat, BigDecimal rango) {
        return repos.buscarInmueblesEnLugarEnRango(lon, lat, rango);
    }

    @Override
    public List<Inmueble> filtrarInmuebles(BigDecimal minArea, BigDecimal maxArea,
                                           BigDecimal minPrecio, BigDecimal maxPrecio,
                                           BigDecimal latitud, BigDecimal longitud, BigDecimal radio, String tipo) {
        return repos.filtrarInmuebles(minArea, maxArea,minPrecio, maxPrecio, latitud, longitud, radio, tipo);
    }

    @Override
    public List<Inmueble> listarFavoritosPorUsuario(int idUsuario) {
        return repos.listarFavoritosPorUsuario(idUsuario);
    }


}
