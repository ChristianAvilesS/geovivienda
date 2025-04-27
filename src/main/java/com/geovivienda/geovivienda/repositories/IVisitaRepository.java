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
        "WHERE EXTRACT(DAY FROM v.fechaVisita) = EXTRACT(DAY FROM :fecha) " +
        " AND v.inmueble.idInmueble= :id")
    List<Visita> buscarVisitaPorInmuebleYFecha(@Param("fecha") Instant fecha, @Param("id") Integer id);
}
