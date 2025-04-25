package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
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
           
    @Query("SELECT i FROM Inmueble i\n" +
            " JOIN Direccion d ON i.direccion = d\n" +
            " WHERE d.longitud BETWEEN (:lon - :rango) AND (:lon + :rango)\n" +
            " AND d.latitud BETWEEN (:lat - :rango) AND (:lat + :rango)\n" +
            " AND i.estado = 'DISPONIBLE'\n" +
            " ORDER BY i.idInmueble")
    List<Inmueble> buscarInmueblesEnLugarEnRango(@Param("lon") BigDecimal lon, @Param("lat") BigDecimal lat,
                                                    @Param("rango") BigDecimal rango);
}
