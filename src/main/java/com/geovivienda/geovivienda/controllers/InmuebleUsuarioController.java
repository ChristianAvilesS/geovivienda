package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.InmuebleUsuarioDTO;
import com.geovivienda.geovivienda.entities.InmuebleUsuario;
import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleUsuarioService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/inmuebleusuario")
public class InmuebleUsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(RolUsuarioController.class);
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IInmuebleUsuarioService servicio;

    @GetMapping("/")
    public List<InmuebleUsuarioDTO> obtenerInmuebleUsuarios() {
        var inmueblesUsuarios = servicio.listarInmuebleUsuarios();
        inmueblesUsuarios.forEach((i) -> logger.info(i.toString()));
        return inmueblesUsuarios.stream().map(i->modelM.map(i, InmuebleUsuarioDTO.class)).collect(Collectors.toList());
    }

    @PostMapping("/")
    public InmuebleUsuarioDTO agregarInmuebleUsuario(@RequestBody InmuebleUsuario inmuebleUsuario) {
        logger.info("inmueble a ingresa: "+ inmuebleUsuario);
        var inmuebleUsuarioGuardado = this.servicio.guardarInmuebleUsuario(inmuebleUsuario);
        return modelM.map(inmuebleUsuarioGuardado, InmuebleUsuarioDTO.class);
    }

    @GetMapping("/id/")
    public ResponseEntity<InmuebleUsuarioDTO> obtenerInmuebleUsuarioPorId(@RequestBody InmuebleUsuarioId id) {
        InmuebleUsuario inmuebleUsuario = servicio.buscarInmuebleUsuarioPorId(id);
        if(inmuebleUsuario != null){
            return ResponseEntity.ok(modelM.map(inmuebleUsuario, InmuebleUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró el id: " + id);
    }

    @DeleteMapping("/")
    public ResponseEntity<Map<String,Boolean>> eliminarInmuebleUsuario(@RequestBody InmuebleUsuarioId id) {
        var inmuebleUsuario = servicio.buscarInmuebleUsuarioPorId(id);
        servicio.eliminarInmuebleUsuario(inmuebleUsuario);
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }
}
