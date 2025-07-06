package com.geovivienda.geovivienda.repositories;


import com.geovivienda.geovivienda.entities.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface IVisitaRepository extends JpaRepository<Visita, Integer> {
    @Query("SELECT v FROM Visita v "+
        " WHERE DATE(v.fechaVisita) = DATE(:fecha) " +
        " AND v.inmueble.idInmueble= :id")
    List<Visita> buscarVisitaPorInmuebleYFecha(@Param("fecha") Instant fecha, @Param("id") Integer id);

    @Query("SELECT v FROM Visita v " +
            "WHERE v.usuario.idUsuario = :idUsuario " +
            "AND v.fechaVisita >= CURRENT_TIMESTAMP")
    List<Visita> listarPorUsuario(@Param("idUsuario") int idUsuario);

    @Query("SELECT v FROM Visita v " +
            " JOIN InmuebleUsuario iu on v.inmueble.idInmueble = iu.inmueble.idInmueble"+
            " WHERE iu.usuario.idUsuario = :idUsuario " +
            " AND iu.esDuenio = true"+
            " AND v.fechaVisita >= CURRENT_TIMESTAMP")
    List<Visita> listarPorUsuarioVendedor(@Param("idUsuario") int idUsuario);

    @Query("SELECT v FROM Visita v " +
            "WHERE v.usuario.idUsuario = :idUsuario " +
            "AND v.fechaVisita < CURRENT_TIMESTAMP")
    List<Visita> listarPorUsuarioHistorial(@Param("idUsuario") int idUsuario);

    @Query("SELECT v FROM Visita v " +
            " JOIN InmuebleUsuario iu on v.inmueble.idInmueble = iu.inmueble.idInmueble"+
            " WHERE iu.usuario.idUsuario = :idUsuario " +
            " AND iu.esDuenio = true"+
            " AND v.fechaVisita < CURRENT_TIMESTAMP")
    List<Visita> listarPorUsuarioVendedorHistorial(@Param("idUsuario") int idUsuario);


    @Query("SELECT COUNT(v) > 0 FROM Visita v " +
            "WHERE v.usuario.idUsuario = :idUsuario " +
            "AND v.inmueble.idInmueble = :idInmueble")
    boolean verificarCitaAgendadaUsuarioInmueble(
            @Param("idUsuario") int idUsuario,
            @Param("idInmueble") int idInmueble);

}
