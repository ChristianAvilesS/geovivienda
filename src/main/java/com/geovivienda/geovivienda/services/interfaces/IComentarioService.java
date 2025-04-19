package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Comentario;

import java.util.List;

public interface IComentarioService {
    public List<Comentario> listarComentario();
    public Comentario buscarcomentarioPorId(Integer id);

    Comentario guardarComentario(Comentario comentario);
}
