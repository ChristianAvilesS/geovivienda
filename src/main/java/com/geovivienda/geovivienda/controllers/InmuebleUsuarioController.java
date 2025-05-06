package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.InmuebleUsuarioDTO;
import com.geovivienda.geovivienda.entities.InmuebleUsuario;
import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleService;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleUsuarioService;
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
@RequestMapping("geovivienda/inmueblesusuario")
public class InmuebleUsuarioController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IInmuebleUsuarioService servicio;

    @Autowired
    private IInmuebleService inmuebleService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<InmuebleUsuarioDTO> obtenerInmuebleUsuarios() {
        return servicio.listarInmuebleUsuarios().stream()
                .map(i-> modelM.map(i, InmuebleUsuarioDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public InmuebleUsuarioDTO agregarInmuebleUsuario(@RequestBody InmuebleUsuarioDTO dto) {
        var iu = modelM.map(dto, InmuebleUsuario.class);
        iu.setInmueble(inmuebleService.buscarInmueblePorId(dto.getInmueble().getIdInmueble()));
        iu.setUsuario(usuarioService.buscarUsuarioPorId(dto.getUsuario().getIdUsuario()));
        return modelM.map(servicio.guardarInmuebleUsuario(iu), InmuebleUsuarioDTO.class);
    }

    @GetMapping("/buscar")
    public ResponseEntity<InmuebleUsuarioDTO> obtenerInmuebleUsuarioPorId(@RequestBody InmuebleUsuarioId id) {
        InmuebleUsuario inmuebleUsuario = servicio.buscarInmuebleUsuarioPorId(id);
        if(inmuebleUsuario != null){
            return ResponseEntity.ok(modelM.map(inmuebleUsuario, InmuebleUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró el id: " + id);
    }

    @DeleteMapping
    public ResponseEntity<Map<String,Boolean>> eliminarInmuebleUsuario(@RequestBody InmuebleUsuarioId id) {
        var inmuebleUsuario = servicio.buscarInmuebleUsuarioPorId(id);
        servicio.eliminarInmuebleUsuario(inmuebleUsuario);
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/marcardesmarcarfavorito")
    public ResponseEntity<Map<String,Boolean>> marcarDesmarcarFavorito(@RequestParam int idInmueble, @RequestParam int idUsuario) {
        var inmuebleUsuario = servicio.marcarDesmarcarFavorito(idInmueble, idUsuario);
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("esFavorito", inmuebleUsuario.getEsFavorito());
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/solicitarcomprainmueble")
    public ResponseEntity<InmuebleUsuarioDTO> solicitarCompraInmueble(@RequestParam int idInmueble,
                                                                      @RequestParam int idUsuario) {
        var inmuebleUsuario = servicio.solicitarCompraInmueble(idInmueble, idUsuario);
        if(inmuebleUsuario != null){
            return ResponseEntity.ok(modelM.map(inmuebleUsuario, InmuebleUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró la relación con idInmueble: " + idInmueble +
                " - idUsuario: " + idUsuario);
    }

    @PutMapping("/aprobarcomprainmueble")
    public ResponseEntity<InmuebleUsuarioDTO> aprobarCompraInmueble(@RequestParam int idInmueble,
                                                                      @RequestParam int idUsuario) {
        var inmuebleUsuario = servicio.aprobarCompraInmueble(idInmueble, idUsuario);
        if(inmuebleUsuario != null){
            return ResponseEntity.ok(modelM.map(inmuebleUsuario, InmuebleUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró la relación con idInmueble: " + idInmueble +
                " - idUsuario: " + idUsuario);
    }

    @PutMapping("/rechazarcomprainmueble")
    public ResponseEntity<InmuebleUsuarioDTO> rechazarCompraInmueble(@RequestParam int idInmueble,
                                                                    @RequestParam int idUsuario) {
        var inmuebleUsuario = servicio.rechazarCompraInmueble(idInmueble, idUsuario);
        if(inmuebleUsuario != null){
            return ResponseEntity.ok(modelM.map(inmuebleUsuario, InmuebleUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró la relación con idInmueble: " + idInmueble +
                " - idUsuario: " + idUsuario);
    }
}
