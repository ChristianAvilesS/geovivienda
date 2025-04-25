package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.RolUsuario;
import com.geovivienda.geovivienda.entities.ids.RolUsuarioId;
import com.geovivienda.geovivienda.repositories.IRolUsuarioRepository;
import com.geovivienda.geovivienda.services.interfaces.IRolUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolUsuarioServiceImpl implements IRolUsuarioService {

    @Autowired
    private IRolUsuarioRepository repos;

    @Override
    public List<RolUsuario> listarRolesUsuario() {
        return repos.findAll();
    }

    @Override
    public RolUsuario buscarRolUsuarioPorId(RolUsuarioId id) {
        return repos.findById(id).orElse(null);
    }

    @Override
    public RolUsuario guardarRolUsuario(RolUsuario rolUsuario) {
        return repos.save(rolUsuario);
    }

    @Override
    public void eliminarRolUsuario(RolUsuario rolUsuario) {
        repos.delete(rolUsuario);
    }

    @Override
    public List<RolUsuario> buscarRolesPorUsuario(int idUsuario) {
        return repos.buscarRolesPorUsuario(idUsuario);
    }

    @Override
    public List<String[]> findPredominantUserRol(int idUsuario) {
        return repos.findPredominantUserRol(idUsuario);
    }
}
