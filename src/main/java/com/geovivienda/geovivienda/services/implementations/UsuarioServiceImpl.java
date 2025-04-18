package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Usuario;
import com.geovivienda.geovivienda.repositories.IUsuarioRepository;
import com.geovivienda.geovivienda.services.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository repos;

    @Override
    public List<Usuario> listarUsuarios() {
        return repos.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer id) {
        return repos.findById(id).orElse(null);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return repos.save(usuario);
    }

    @Override
    public Usuario eliminarUsuario(Usuario usuario) {
        //No se usa esto porque "eliminar" está como atributo en inactivo: repos.delete(usuario);
        usuario.setInactivo(true);
        return guardarUsuario(usuario);
    }

    @Override
    public Usuario verificarLogin(Usuario usuario) {
        return repos.buscarUsuarioPorUsernameYPassword(usuario.getUsername(), usuario.getPassword());
    }
}
