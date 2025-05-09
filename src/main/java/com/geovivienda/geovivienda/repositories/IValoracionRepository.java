package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IValoracionRepository extends JpaRepository<Valoracion, Integer> {
    @Query(value = "SELECT i.nombre, COUNT(v.id_valoracion) AS CantidadValoraciones \n" +
            "FROM inmuebles i\n" +
            "LEFT JOIN valoraciones v ON v.id_inmueble = i.id_inmueble\n" +
            "GROUP BY i.nombre\n", nativeQuery = true)
    List<String[]> cantidadValoracionesXInmueble();


    @Query(value="SELECT i.nombre, AVG(v.rating) AS PromedioValoracion\n" +
            "FROM inmuebles i\n" +
            "LEFT JOIN valoraciones v ON v.id_inmueble = i.id_inmueble\n" +
            "GROUP BY i.nombre;", nativeQuery = true)
    List<String[]> valoracionInmueble();
}
