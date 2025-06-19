package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImagenRepository extends JpaRepository<Imagen, Integer> {

    @Query(value = "SELECT inm.id_inmueble, inm.nombre, " +
            "COUNT(img.id_imagen) " +
            "FROM inmuebles inm " +
            "LEFT JOIN imagenes img ON img.id_inmueble = inm.id_inmueble " +
            "GROUP BY inm.id_inmueble, inm.nombre", nativeQuery = true)
    public List<String[]> cantidadImagenesXInmueble();

    @Query(value = "select i from Imagen i where i.inmueble.idInmueble = :idInmueble")
    public List<Imagen> buscarPorIdInmueble(@Param("idInmueble") int idInmueble);
}


