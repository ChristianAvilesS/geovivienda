package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Visita;

import java.util.List;

public interface IVisitaService {
    public List<Visita> listarVisitas();
    public Visita buscarVisitaPorId(Integer id);

    Visita guardarVisita(Visita visita);
}
