package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInmuebleRepository extends JpaRepository<Inmueble, Integer> {

    @Query(value = """
        SELECT * FROM Inmueble i
        JOIN Direccion d ON i.id_direccion = d.id_direccion
        WHERE d.longitud BETWEEN :minLong AND :maxLong
        AND d.latitud BETWEEN :minLat AND :maxLat
        """, nativeQuery = true)
    public List<Inmueble> buscarInmueblesCercanosUsuario(
            @Param("minLong") double minLong,
            @Param("maxLong") double maxLong,
            @Param("minLat") double minLat,
            @Param("maxLat") double maxLat
    );


}
