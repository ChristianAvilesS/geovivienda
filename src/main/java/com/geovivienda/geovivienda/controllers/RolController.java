package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.RolDTO;
import com.geovivienda.geovivienda.entities.Rol;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IRolService;
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
@RequestMapping("geovivienda/roles")
public class RolController {
    private static final Logger logger = LoggerFactory.getLogger(RolController.class);
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IRolService servicio;

    @GetMapping("/")
    public List<RolDTO> obtenerRols() {
        var roles = servicio.listarRoles();
        roles.forEach((rol) -> logger.info(rol.toString()));
        return roles.stream().map(p -> modelM.map(p, RolDTO.class)).collect(Collectors.toList());
    }

    @PostMapping("/")
    public RolDTO agregarRol(@RequestBody Rol rol) {
        logger.info("Rol a agregar: " + rol);
        var rolGuardado = this.servicio.guardarRol(rol);
        return modelM.map(rolGuardado, RolDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> obtenerRolPorId(@PathVariable int id) {
        Rol rol = servicio.buscarRolPorId(id);
        if (rol != null) {
            return ResponseEntity.ok(modelM.map(rol, RolDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró el id: " + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> actualizarRol(@PathVariable int id, @RequestBody Rol rolRecibido) {
        Rol rol = this.servicio.buscarRolPorId(id);
        rol.setRol(rolRecibido.getRol());

        this.servicio.guardarRol(rol);
        return ResponseEntity.ok(modelM.map(rol, RolDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarRol(@PathVariable int id) {
        var rol = servicio.buscarRolPorId(id);
        servicio.eliminarRol(rol);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }


}
