package com.geovivienda.geovivienda.services.implementations;
import com.geovivienda.geovivienda.entities.Comentario;
import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.repositories.IComentarioRepository;
import com.geovivienda.geovivienda.services.interfaces.IComentarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ComentarioServicelmpl implements  IComentarioService {

    private final ModelMapper modelM = new ModelMapper();
    @Autowired
    private IComentarioRepository repos;

    @Override
    public List<Comentario> listarComentario() {
        return repos.findAll();
    }

    @Override
    public Comentario buscarcomentarioPorId(Integer id) {
        return repos.findById(id).orElse(null) ;
    }

    @Override
    public Comentario editarComentario(Comentario comentario) {
        return repos.save(comentario);
    }

    @Override
    public Comentario guardarComentario(Comentario comentario) {
        return repos.save(comentario);
    }

    @Override
    public List<Comentario> findByInmueble(int idInmueble) {
        return repos.findByInmueble(idInmueble);
    }

    @Override
    public Comentario findById(Integer id) {
        return repos.findById(id).orElse(null);
    }

    @Override
    public Comentario findByUseryInmueble(int idUsuario, int idInmueble) {
        return repos.findByUseryInmueble(idUsuario, idInmueble);
    }

}
