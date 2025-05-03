package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Inmueble;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface IInmuebleService {

    List<Inmueble> listarInmuebles();
    Inmueble buscarInmueblePorId(Integer id);
    Inmueble guardarInmueble(Inmueble inmueble);
    void eliminarInmueble(Inmueble inmueble);
    List<Inmueble> buscarInmueblesEnLugarEnRango(BigDecimal lon, BigDecimal lat, BigDecimal rango);
    List<Inmueble> filtrarInmuebles(BigDecimal minArea, BigDecimal maxArea,
                                    BigDecimal minPrecio, BigDecimal maxPrecio,
                                    BigDecimal latitud, BigDecimal longitud,
                                    BigDecimal radio, String tipo);
}
