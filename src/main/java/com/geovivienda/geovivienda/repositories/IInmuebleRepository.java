package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IInmuebleRepository extends JpaRepository<Inmueble, Integer> {
    @Query("SELECT i FROM Inmueble i\n" +
            " JOIN Direccion d ON i.direccion = d\n" +
            " WHERE d.longitud BETWEEN (:lon - :rango) AND (:lon + :rango)\n" +
            " AND d.latitud BETWEEN (:lat - :rango) AND (:lat + :rango)\n" +
            " AND i.estado = 'DISPONIBLE'\n" +
            " ORDER BY i.idInmueble")
    List<Inmueble> buscarInmueblesEnLugarEnRango(@Param("lon") BigDecimal lon, @Param("lat") BigDecimal lat,
                                                 @Param("rango") BigDecimal rango);

    @Query(value = "select i from Inmueble i join Direccion d on i.direccion.idDireccion = d.idDireccion \n" +
            " where d.longitud between (:longitud - :rango) and (:longitud + :rango) and\n" +
            " d.latitud between (:latitud -:rango) and (:latitud + :rango) and \n" +
            " i.estado = 'DISPONIBLE' and\n" +
            " (:minArea is null or i.area >= :minArea) and\n" +
            " (:maxArea is null or i.area <= :maxArea) and\n" +
            " (:minPrecio is null or i.precioBase >= :minPrecio) and\n" +
            " (:maxPrecio is null or i.precioBase <= :maxPrecio) and\n" +
            " (:tipo is null or i.tipo like :tipo)\n" +
            " order by i.idInmueble"
    )
    List<Inmueble> filtrarInmuebles(@Param("minArea") BigDecimal minArea, @Param("maxArea") BigDecimal maxArea,
                                    @Param("minPrecio") BigDecimal minPrecio, @Param("maxPrecio") BigDecimal maxPrecio,
                                    @Param("latitud") BigDecimal latitud, @Param("longitud") BigDecimal longitud,
                                    @Param("rango") BigDecimal rango, @Param("tipo") String tipo);

    @Query(value = "select iu.inmueble from InmuebleUsuario iu \n" +
            " where iu.usuario.idUsuario = :idUsuario and iu.esFavorito = true and iu.inmueble.estado != 'ELIMINADO'")
    List<Inmueble> listarFavoritosPorUsuario(@Param("idUsuario") int idUsuario);

    @Modifying
    @Query("UPDATE Inmueble i SET i.estado = 'ELIMINADO' " +
            "WHERE i.idInmueble = :id")
    void deleteLogically(@Param("id") int id);
}
