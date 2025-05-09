package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Valoracion;

import java.util.List;

public interface IValoracionService {
     List<Valoracion> listarValoraciones();
     Valoracion buscarValoracionPorId(Integer id);

     Valoracion guardarValoracion(Valoracion valoracion);
     void eliminarValoracion(Valoracion valoracion);

     List<String[]> cantidadValoracionesXInmueble();
     List<String[]> valoracionInmueble();
}
