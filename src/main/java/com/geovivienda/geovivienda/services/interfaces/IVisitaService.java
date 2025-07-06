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

    List<Visita> listarPorUsuario(int idUsuario);

    List<Visita> listarPorUsuarioHistorial(int idUsuario);

    List<Visita> listarPorUsuarioVendedor(int idUsuario);

    List<Visita> listarPorUsuarioVendedorHistorial(int idUsuario);

    void eliminarVisita(Visita visita);

    boolean verificarCitaAgendadaUsuarioInmueble(int idUsuario, int idInmueble);
}
