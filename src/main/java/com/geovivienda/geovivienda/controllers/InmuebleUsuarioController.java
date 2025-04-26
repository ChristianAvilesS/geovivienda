package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.InmuebleUsuarioDTO;
import com.geovivienda.geovivienda.entities.InmuebleUsuario;
import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public List<InmuebleUsuarioDTO> obtenerInmuebleUsuarios() {
        return servicio.listarInmuebleUsuarios().stream()
                .map(i-> modelM.map(i, InmuebleUsuarioDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public InmuebleUsuarioDTO agregarInmuebleUsuario(@RequestBody InmuebleUsuarioDTO dto) {
        return modelM.map(servicio.guardarInmuebleUsuario(modelM.map(dto, InmuebleUsuario.class))
                , InmuebleUsuarioDTO.class);
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
}
