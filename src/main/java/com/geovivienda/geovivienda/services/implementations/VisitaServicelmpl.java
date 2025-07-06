package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Visita;
import com.geovivienda.geovivienda.repositories.IVisitaRepository;
import com.geovivienda.geovivienda.services.interfaces.IVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    @Override
    public List<Visita> buscarVisitaPorInmuebleYFecha(Instant fecha, Integer id) {
        return repos.buscarVisitaPorInmuebleYFecha(fecha, id);
    }

    @Override
    public List<Visita> listarPorUsuario(int idUsuario) {
        return repos.listarPorUsuario(idUsuario);
    }

    @Override
    public List<Visita> listarPorUsuarioHistorial(int idUsuario) {
        return repos.listarPorUsuarioHistorial(idUsuario);
    }

    @Override
    public List<Visita> listarPorUsuarioVendedor(int idUsuario) {
        return repos.listarPorUsuarioVendedor(idUsuario);
    }

    @Override
    public List<Visita> listarPorUsuarioVendedorHistorial(int idUsuario) {
        return repos.listarPorUsuarioVendedorHistorial(idUsuario);
    }

    @Override
    public void eliminarVisita(Visita visita) {
        repos.delete(visita);
    }

    @Override
    public boolean verificarCitaAgendadaUsuarioInmueble(int idUsuario, int idInmueble) {
        return repos.verificarCitaAgendadaUsuarioInmueble(idUsuario, idInmueble);
    }


}
