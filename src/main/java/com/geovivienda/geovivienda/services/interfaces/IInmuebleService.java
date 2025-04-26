package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Inmueble;
import java.math.BigDecimal;
import java.util.List;

public interface IInmuebleService {

    List<Inmueble> listarInmuebles();
    Inmueble buscarInmueblePorId(Integer id);
    Inmueble guardarInmueble(Inmueble inmueble);
    void eliminarInmueble(Inmueble inmueble);
    List<Inmueble> buscarInmueblesEnLugarEnRango(BigDecimal lon, BigDecimal lat, BigDecimal rango);
}
