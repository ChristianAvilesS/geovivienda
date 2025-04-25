package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Rol;
import com.geovivienda.geovivienda.repositories.IRolRepository;
import com.geovivienda.geovivienda.services.interfaces.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
