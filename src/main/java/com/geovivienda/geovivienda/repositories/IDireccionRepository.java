package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

public interface IDireccionRepository extends JpaRepository<Direccion, Integer> {
    @Query("SELECT dir FROM Direccion dir WHERE dir.latitud" +
            " BETWEEN :lat - :rango AND :lat + :rango " +
            "AND dir.longitud BETWEEN :lon - :rango AND :lon + :rango")
    List<Direccion> buscarDireccionesEnRango(@Param("lat") BigDecimal lat, @Param("lon") BigDecimal lon,
                                             @Param("rango") BigDecimal rango);
}
