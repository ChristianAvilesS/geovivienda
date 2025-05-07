package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.InmuebleUsuario;
import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;

import java.util.List;

public interface IInmuebleUsuarioService {

    List<InmuebleUsuario> listarInmuebleUsuarios();
    InmuebleUsuario buscarInmuebleUsuarioPorId(InmuebleUsuarioId id);
    InmuebleUsuario guardarInmuebleUsuario(InmuebleUsuario usuario);
    void eliminarInmuebleUsuario(InmuebleUsuario usuario);
    InmuebleUsuario marcarDesmarcarFavorito(int idInmueble, int idUsuario);
    InmuebleUsuario solicitarCompraInmueble(int idInmueble, int idUsuario);
    InmuebleUsuario aprobarCompraInmueble(int idInmueble, int idUsuario);
    InmuebleUsuario rechazarCompraInmueble(int idInmueble, int idUsuario);
    InmuebleUsuario finalizarCompraInmueble(int idInmueble, int idUsuarioVendedor, int idUsuarioComprador);
}
