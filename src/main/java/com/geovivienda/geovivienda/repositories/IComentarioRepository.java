package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Comentario;
import com.geovivienda.geovivienda.entities.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IComentarioRepository extends JpaRepository<Comentario, Integer> {
    @Query(value = "select c from Comentario c where c.inmueble.idInmueble = :idInmueble ")
    List<Comentario> findByInmueble(@Param("idInmueble") int idInmueble );

    @Query(value = " select c from Comentario c where c.inmueble.idInmueble = :idInmueble and c.usuario.idUsuario = :idUsuario ")
    public Comentario findByUseryInmueble(@Param("idUsuario") int idUsuario, @Param("idInmueble")int idInmueble);
}


