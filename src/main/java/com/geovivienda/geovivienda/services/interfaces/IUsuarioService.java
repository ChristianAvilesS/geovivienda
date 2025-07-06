package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Usuario;

import java.util.List;

public interface IUsuarioService {

    List<Usuario> listarUsuarios();
    Usuario buscarUsuarioPorId(Integer id);

    // Cumple la función de insertar y actualizar
    Usuario guardarUsuario(Usuario usuario);

    Usuario insertarUsuarioYRol(Usuario usuario, int rol);
    Usuario eliminarUsuario(Usuario usuario);
    List<Usuario> listarUsuariosNoEliminados();
    void cambiarPassword(String username, String password);
}
