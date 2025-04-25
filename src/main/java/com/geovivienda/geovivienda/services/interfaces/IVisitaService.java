package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Visita;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface IVisitaService {
    public List<Visita> listarVisitas();
    public Visita buscarVisitaPorId(Integer id);

    Visita guardarVisita(Visita visita);

    List<Visita> buscarVisitaPorInmuebleYFecha( Instant fecha, Integer id);
}
