package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Visita;
import com.geovivienda.geovivienda.repositories.IVisitaRepository;
import com.geovivienda.geovivienda.services.interfaces.IVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitaServicelmpl implements IVisitaService {

    @Autowired
    private IVisitaRepository repos;

    @Override
    public List<Visita> listarVisitas() {
        return repos.findAll();
    }

    @Override
    public Visita buscarVisitaPorId(Integer id) {
        return repos.findById(id).orElse(null);
    }

    @Override
    public Visita guardarVisita(Visita visita) {
        return repos.save(visita);
    }
}
