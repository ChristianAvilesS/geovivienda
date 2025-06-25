package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.InmuebleUsuario;
import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;
import com.geovivienda.geovivienda.repositories.IInmuebleUsuarioRepository;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public InmuebleUsuario findUsuarioDuenioByInmueble(int id) {
        return repo.findUsuarioDuenioByInmueble(id);
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

    @Override
    public InmuebleUsuario solicitarCompraInmueble(int idInmueble, int idUsuario) {
        InmuebleUsuario inmuebleUsuario = repo.buscarPorIdInmuebleIdUsuario(idInmueble, idUsuario);
        if(inmuebleUsuario.getInmueble().getEstado().equals("DISPONIBLE")) {
            inmuebleUsuario.setEstadoSolicitud("SOLICITADO");
            inmuebleUsuario.setFechaSolicitud(LocalDate.now());
            return repo.save(inmuebleUsuario);
        }
        return null;
    }

    @Override
    public InmuebleUsuario aprobarCompraInmueble(int idInmueble, int idUsuario) {
        InmuebleUsuario inmuebleUsuario = repo.buscarPorIdInmuebleIdUsuario(idInmueble, idUsuario);
        if(inmuebleUsuario.getEstadoSolicitud().equals("SOLICITADO")) {
            inmuebleUsuario.setEstadoSolicitud("APROBADO");
            return repo.save(inmuebleUsuario);
        }
        return null;
    }

    @Override
    public InmuebleUsuario rechazarCompraInmueble(int idInmueble, int idUsuario) {
        InmuebleUsuario inmuebleUsuario = repo.buscarPorIdInmuebleIdUsuario(idInmueble, idUsuario);
        if(inmuebleUsuario.getEstadoSolicitud().equals("SOLICITADO")) {
            inmuebleUsuario.setEstadoSolicitud("RECHAZADO");
            return repo.save(inmuebleUsuario);
        }
        return null;
    }

    @Override
    public InmuebleUsuario finalizarCompraInmueble(int idInmueble, int idUsuarioVendedor, int idUsuarioComprador) {
        InmuebleUsuario inmuebleUsuarioVendedor = repo.buscarPorIdInmuebleIdUsuario(idInmueble, idUsuarioVendedor);
        InmuebleUsuario inmuebleUsuarioComprador = repo.buscarPorIdInmuebleIdUsuario(idInmueble, idUsuarioComprador);
        if(inmuebleUsuarioComprador.getEstadoSolicitud().equals("APROBADO")) {
            inmuebleUsuarioComprador.setEstadoSolicitud("COMPRADO");
            inmuebleUsuarioComprador.setEsDuenio(true);
            inmuebleUsuarioVendedor.setEsDuenio(false);
            return repo.save(inmuebleUsuarioComprador);
        }
        return null;
    }

    @Override
    public List<InmuebleUsuario> listarEstadoSolicitadoPorIdUsuarioVendedor(int idUsuario) {
        return repo.buscarPorEstadoSolicitadIdUsuarioVendedor(idUsuario);
    }
}
