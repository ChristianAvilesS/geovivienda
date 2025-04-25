package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.ComentarioDTO;
import com.geovivienda.geovivienda.entities.Comentario;
import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IComentarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/comentarios")
public class ComentarioController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IComentarioService servicio;

    @GetMapping
    public List<ComentarioDTO> obtenerComentarios() {
        return servicio.listarComentario().stream()
                .map(p -> modelM.map(p, ComentarioDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public ComentarioDTO agregarComentario(@RequestBody ComentarioDTO dto) {
        return modelM.map(this.servicio.guardarComentario(modelM.map(dto, Comentario.class)), ComentarioDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable int id) {
        Comentario comentario = servicio.buscarcomentarioPorId(id);

        if (comentario != null) {
            return ResponseEntity.ok(modelM.map(comentario, ComentarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontro el id: " + id);
    }

    @GetMapping("/buscarporinmueble/{id}")
    public List<ComentarioDTO> findByInmueble(@PathVariable Integer id) {

        Inmueble inmueble = servicio.findById(id).getInmueble();
        return servicio.findByInmueble(inmueble).stream()
                .map(p -> modelM.map(p, ComentarioDTO.class)).collect(Collectors.toList());
    }
}
