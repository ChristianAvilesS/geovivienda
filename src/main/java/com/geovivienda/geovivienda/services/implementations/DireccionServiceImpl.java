package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Direccion;
import com.geovivienda.geovivienda.repositories.IDireccionRepository;
import com.geovivienda.geovivienda.services.interfaces.IDireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionServiceImpl implements IDireccionService {

    @Autowired
    private IDireccionRepository repos;

    @Override
    public List<Direccion> listarDirecciones() {
        return repos.findAll();
    }

    @Override
    public Direccion buscarDireccionPorId(Integer id) {
        return repos.findById(id).orElse(null);
    }

    @Override
    public Direccion guardarDireccion(Direccion direccion) {
        return repos.save(direccion);
    }

    @Override
    public void eliminarDireccion(Direccion direccion) {
        repos.delete(direccion);
    }
}
