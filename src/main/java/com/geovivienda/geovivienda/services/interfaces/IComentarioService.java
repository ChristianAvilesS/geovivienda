package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Comentario;
import com.geovivienda.geovivienda.entities.Inmueble;

import java.util.List;

public interface IComentarioService {
    public List<Comentario> listarComentario();

    public Comentario buscarcomentarioPorId(Integer id);

    Comentario editarComentario(Comentario comentario);

    Comentario guardarComentario(Comentario comentario);

    List<Comentario> findByInmueble(int idInmueble);

    Comentario findById(Integer id);

    Comentario findByUseryInmueble(int idUsuario, int idInmueble);
}
