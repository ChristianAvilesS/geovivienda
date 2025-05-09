package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.InmuebleUsuario;
import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.List;

@Repository
public interface IInmuebleUsuarioRepository extends JpaRepository<InmuebleUsuario, InmuebleUsuarioId> {

    @Query(value = "select iu from InmuebleUsuario iu where iu.inmueble.idInmueble = :idInmueble and iu.usuario.idUsuario = :idUsuario")
    InmuebleUsuario buscarPorIdInmuebleIdUsuario(@Param("idInmueble") int idInmueble, @Param("idUsuario")  int idUsuario);

    @Query("SELECT i FROM InmuebleUsuario i " +
            "WHERE i.id.idUsuario = :id")
    List<InmuebleUsuario> findInmueblesByUser(@Param("id") int id);

    @Modifying
    @Query("DELETE InmuebleUsuario iu WHERE iu.inmueble.idInmueble = :id")
    void deleteInmueblesUsuarioByInmueble(@Param("id") int id);
  
    @Query(value = "select iu from InmuebleUsuario iu where iu.usuario.idUsuario = :idUsuario and iu.estadoSolicitud like 'SOLICITADO'")
    List<InmuebleUsuario> buscarPorEstadoSolicitadIdUsuarioVendedor(@Param("idUsuario")  int idUsuario);
  }
