package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.InmuebleUsuario;
import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IInmuebleUsuarioRepository extends JpaRepository<InmuebleUsuario, InmuebleUsuarioId> {

    @Query(value = "select iu from InmuebleUsuario iu where iu.inmueble.idInmueble = :idInmueble and iu.usuario.idUsuario = :idUsuario")
    InmuebleUsuario buscarPorIdInmuebleIdUsuario(@Param("idInmueble") int idInmueble, @Param("idUsuario")  int idUsuario);
}
