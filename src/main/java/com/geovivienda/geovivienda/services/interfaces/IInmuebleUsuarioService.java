package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.InmuebleUsuario;
import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;

import java.util.List;

public interface IInmuebleUsuarioService {

    public List<InmuebleUsuario> listarInmuebleUsuarios();
    public InmuebleUsuario buscarInmuebleUsuarioPorId(InmuebleUsuarioId id);

    public InmuebleUsuario guardarInmuebleUsuario(InmuebleUsuario usuario);
    public void eliminarInmuebleUsuario(InmuebleUsuario usuario);
}
