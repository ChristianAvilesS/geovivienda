package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.ComentarioDTO;
import com.geovivienda.geovivienda.dtos.InmuebleDTO;
import com.geovivienda.geovivienda.entities.Comentario;
import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.entities.Usuario;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IComentarioService;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleService;
import com.geovivienda.geovivienda.services.interfaces.IUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/comentarios")
public class ComentarioController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IComentarioService servicio;

    @Autowired
    private IUsuarioService userService;

    @Autowired
    private IInmuebleService inmuebleService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ComentarioDTO> obtenerComentarios() {
        return servicio.listarComentario().stream()
                .map(p -> modelM.map(p, ComentarioDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public ComentarioDTO agregarComentario(@RequestBody ComentarioDTO dto) {
        var comentario = modelM.map(dto, Comentario.class);
        comentario.setIdComentario(null);
        comentario.setInmueble(inmuebleService.buscarInmueblePorId(dto.getInmueble().getIdInmueble()));
        comentario.setUsuario(userService.buscarUsuarioPorId(dto.getUsuario().getIdUsuario()));
        return modelM.map(this.servicio.guardarComentario(comentario), ComentarioDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable int id) {
        Comentario comentario = servicio.buscarcomentarioPorId(id);

        if (comentario != null) {
            return ResponseEntity.ok(modelM.map(comentario, ComentarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontro el id: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Boolean>> eliminarComentario(@PathVariable int id) {
        var comentario = servicio.buscarcomentarioPorId(id);
        servicio.eliminarComentario(comentario);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/buscarporinmueble/{id}")
    public List<ComentarioDTO> findByInmueble(@PathVariable Integer id) {
        return servicio.findByInmueble(id).stream()
                .map(p -> modelM.map(p, ComentarioDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/buscarporusuarioinmueble")
    public ResponseEntity<ComentarioDTO> findByUseryInmueble(@RequestParam int idUsuario, @RequestParam int idInmueble)
    {
        Comentario comentario = servicio.findByUseryInmueble(idUsuario, idInmueble);
        return ResponseEntity.ok(modelM.map(comentario, ComentarioDTO.class));
    }

    @PutMapping
    public Comentario editarComentario(@RequestBody ComentarioDTO dto) {

        Inmueble inmueble = inmuebleService.buscarInmueblePorId(dto.getInmueble().getIdInmueble());
        Usuario usuario = userService.buscarUsuarioPorId(dto.getUsuario().getIdUsuario());

        Comentario comentario = new Comentario();
        comentario.setIdComentario(dto.getIdComentario());
        comentario.setDescripcion(dto.getDescripcion());
        comentario.setInmueble(inmueble);
        comentario.setUsuario(usuario);

        return servicio.editarComentario(comentario);
    }

}
