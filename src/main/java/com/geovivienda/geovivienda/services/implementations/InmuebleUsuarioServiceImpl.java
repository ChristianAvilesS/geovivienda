package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.InmuebleUsuario;
import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;
import com.geovivienda.geovivienda.repositories.IInmuebleUsuarioRepository;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InmuebleUsuarioServiceImpl implements IInmuebleUsuarioService {

    @Autowired
    private IInmuebleUsuarioRepository repo;

    @Override
    public List<InmuebleUsuario> listarInmuebleUsuarios() {
        return repo.findAll();
    }

    @Override
    public InmuebleUsuario buscarInmuebleUsuarioPorId(InmuebleUsuarioId id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public InmuebleUsuario guardarInmuebleUsuario(InmuebleUsuario inmuebleUsuario) {
        return repo.save(inmuebleUsuario);
    }

    @Override
    public void eliminarInmuebleUsuario(InmuebleUsuario inmuebleUsuario) {
        repo.delete(inmuebleUsuario);
    }

    @Override
    public InmuebleUsuario marcarDesmarcarFavorito(int idInmueble, int idUsuario) {
        InmuebleUsuario inmuebleUsuario = repo.buscarPorIdInmuebleIdUsuario(idInmueble, idUsuario);
        inmuebleUsuario.setEsFavorito(!inmuebleUsuario.getEsFavorito());
        return repo.save(inmuebleUsuario);
    }
}
