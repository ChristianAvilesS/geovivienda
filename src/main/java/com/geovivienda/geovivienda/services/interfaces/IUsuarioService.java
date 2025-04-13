package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Usuario;

import java.util.List;

public interface IUsuarioService {

    public List<Usuario> listarUsuarioes();
    public Usuario buscarUsuarioPorId(Integer id);

    // Cumple la función de insertar y actualizar
    public Usuario guardarUsuario(Usuario usuario);
    public Usuario eliminarUsuario(Usuario usuario);
}
