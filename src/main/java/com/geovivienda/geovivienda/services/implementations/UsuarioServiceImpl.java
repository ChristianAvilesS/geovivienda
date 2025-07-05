package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.RolUsuario;
import com.geovivienda.geovivienda.entities.Usuario;
import com.geovivienda.geovivienda.entities.ids.RolUsuarioId;
import com.geovivienda.geovivienda.repositories.*;
import com.geovivienda.geovivienda.services.interfaces.IUsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepos;

    @Autowired
    private IInmuebleUsuarioRepository inmuebleUsuarioRepos;

    @Autowired
    private IInmuebleRepository inmuebleRepos;

    @Autowired
    private IRolUsuarioRepository rolURepos;

    @Autowired
    private IRolRepository rolRepos;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepos.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepos.findById(id).orElse(null);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepos.save(usuario);
    }

    @Override
    public Usuario insertarUsuarioYRol(Usuario usuario, int rol) {
        Usuario u = usuarioRepos.save(usuario);
        RolUsuario rolUsuario = new RolUsuario();
        var rolId = new RolUsuarioId();
        rolId.setIdUsuario(u.getIdUsuario());
        rolId.setIdRol(rol);
        rolUsuario.setId(rolId);
        rolUsuario.setRol(rolRepos.findById(rol).orElse(null));
        rolUsuario.setUsuario(u);
        rolURepos.save(rolUsuario);
        return u;
    }

    @Override
    @Transactional
    public Usuario eliminarUsuario(Usuario usuario) {
        //No se usa esto porque "eliminar" está como atributo en inactivo: repos.delete(usuario);
        usuario.setInactivo(true);

        for (var iu : inmuebleUsuarioRepos.findInmueblesByUser(usuario.getIdUsuario())) {
            if (iu.getEsDuenio()) {
                inmuebleRepos.deleteLogically(iu.getInmueble().getIdInmueble());
                inmuebleUsuarioRepos.deleteInmueblesUsuarioByInmueble(iu.getInmueble().getIdInmueble());
            }
        }

        inmuebleUsuarioRepos.deleteInmueblesUsuarioByUser(usuario.getIdUsuario());
        return guardarUsuario(usuario);
    }

    @Override
    public List<Usuario> listarUsuariosNoEliminados() {
        return usuarioRepos.listNotEliminated();
    }

    @Override
    public void cambiarPassword(String username, String password) {
        var usuario = usuarioRepos.findUsuarioByUsername(username);
        usuario.setPassword(password);
        usuarioRepos.save(usuario);
    }
}
