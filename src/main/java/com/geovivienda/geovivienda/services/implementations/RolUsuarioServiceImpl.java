package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.RolUsuario;
import com.geovivienda.geovivienda.entities.ids.RolUsuarioId;
import com.geovivienda.geovivienda.repositories.IRolUsuarioRepository;
import com.geovivienda.geovivienda.services.interfaces.IRolService;
import com.geovivienda.geovivienda.services.interfaces.IRolUsuarioService;
import com.geovivienda.geovivienda.services.interfaces.IUsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolUsuarioServiceImpl implements IRolUsuarioService {

    @Autowired
    private IRolUsuarioRepository repos;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRolService rolService;

    @Override
    public List<RolUsuario> listarRolesUsuario() {
        return repos.findAll();
    }

    @Override
    public RolUsuario buscarRolUsuarioPorId(RolUsuarioId id) {
        return repos.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public RolUsuario guardarRolUsuario(RolUsuario ru) {
        repos.eliminarRolesDeMayorPrioridad(ru.getId().getIdUsuario(), ru.getId().getIdRol());
        ru.setUsuario(usuarioService.buscarUsuarioPorId(ru.getId().getIdUsuario()));
        ru.setRol(rolService.buscarRolPorId(ru.getId().getIdRol()));
        return repos.save(ru);
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
