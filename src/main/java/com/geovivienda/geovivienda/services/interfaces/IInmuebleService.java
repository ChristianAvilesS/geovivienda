package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Inmueble;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface IInmuebleService {
    public List<Inmueble> listarInmuebles();
    public Inmueble buscarInmueblePorId(Integer id);

    public Inmueble guardarInmueble(Inmueble inmueble);
    public void eliminarInmueble(Inmueble inmueble);

    public List<Inmueble> buscarInmueblesCercanosUsuario(double minLong,double maxLong,double minLat,double maxLat);

    List<Inmueble> listarInmuebles();
    Inmueble buscarInmueblePorId(Integer id);
    Inmueble guardarInmueble(Inmueble inmueble);
    void eliminarInmueble(Inmueble inmueble);
    List<Inmueble> buscarInmueblesEnLugarEnRango(BigDecimal lon, BigDecimal lat, BigDecimal rango);
}
