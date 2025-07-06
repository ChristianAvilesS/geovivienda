package com.geovivienda.geovivienda.repositories;


import com.geovivienda.geovivienda.entities.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IAnuncioRepository extends JpaRepository<Anuncio, Integer> {

    @Query(value = " SELECT u.id_usuario, u.nombre, COUNT(a.id_anuncio) AS cantidad_anuncios " +
            "FROM usuarios u " +
            "LEFT JOIN anuncios a ON a.id_anunciante = u.id_usuario " +
            "GROUP BY u.id_usuario, u.nombre ",
            nativeQuery = true)
    public List<String[]> cantidadAnunciosXUsuario();

    @Query(value = "SELECT a FROM Anuncio a " +
            " WHERE a.inmueble.idInmueble = :idInmueble")
    public Anuncio buscarPorInmueble(@Param("idInmueble") int idInmueble);

}
