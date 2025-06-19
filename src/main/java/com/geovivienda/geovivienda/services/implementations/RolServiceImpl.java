package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Rol;
import com.geovivienda.geovivienda.repositories.IRolRepository;
import com.geovivienda.geovivienda.services.interfaces.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolServiceImpl implements IRolService {

    @Autowired
    IRolRepository repos;

    @Override
    public List<Rol> listarRoles() {
        return repos.findAll();
    }

    @Override
    public Rol buscarRolPorId(Integer id) {
        return repos.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Rol insertarRol(Rol rol) {
        int idAdmin = buscarRolPorNombre("ADMIN").getIdRol();
        var role = new Rol();
        role.setRol("ADMIN");
        int nuevoId = guardarRol(role).getIdRol();
        repos.actualizarRolesUsuarioPorCambioAdmin(nuevoId, idAdmin);
        rol.setIdRol(idAdmin);
        return guardarRol(rol);
    }

    @Override
    public Rol guardarRol(Rol rol) {
        return repos.save(rol);
    }

    @Override
    public void eliminarRol(Rol rol) {
        repos.delete(rol);
    }

    @Override
    public Rol buscarRolPorNombre(String nombre) {
        return repos.buscarRolPorNombre(nombre);
    }


}
