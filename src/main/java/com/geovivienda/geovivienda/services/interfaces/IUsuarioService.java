package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Usuario;

import java.util.List;

public interface IUsuarioService {

    List<Usuario> listarUsuarios();
    Usuario buscarUsuarioPorId(Integer id);

    // Cumple la función de insertar y actualizar
    Usuario guardarUsuario(Usuario usuario);
    Usuario eliminarUsuario(Usuario usuario);
    Usuario verificarLogin(Usuario usuario);
}
