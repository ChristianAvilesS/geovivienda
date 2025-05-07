package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Usuario;
import com.geovivienda.geovivienda.repositories.IInmuebleRepository;
import com.geovivienda.geovivienda.repositories.IInmuebleUsuarioRepository;
import com.geovivienda.geovivienda.repositories.IUsuarioRepository;
import com.geovivienda.geovivienda.services.interfaces.IUsuarioService;
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
    public Usuario eliminarUsuario(Usuario usuario) {
        //No se usa esto porque "eliminar" está como atributo en inactivo: repos.delete(usuario);
        usuario.setInactivo(true);

        for (var iu : inmuebleUsuarioRepos.findInmueblesByUser(usuario.getIdUsuario())) {
            inmuebleRepos.deleteLogically(iu.getInmueble().getIdInmueble());
            inmuebleUsuarioRepos.deleteInmueblesUsuarioByInmueble(iu.getInmueble().getIdInmueble());
        }

        return guardarUsuario(usuario);
    }
}
