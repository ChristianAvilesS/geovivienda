package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.RolUsuarioDTO;
import com.geovivienda.geovivienda.entities.PK_RolUsuario;
import com.geovivienda.geovivienda.entities.RolUsuario;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IRolUsuarioService;
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
@RequestMapping("geovivienda/rolesUsuario")
public class RolUsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(RolUsuarioController.class);
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IRolUsuarioService servicio;

    @GetMapping("/")
    public List<RolUsuarioDTO> obtenerRolUsuarios() {
        var rolesUsuario = servicio.listarRolesUsuario();
        rolesUsuario.forEach((rolUsuario) -> logger.info(rolUsuario.toString()));
        return rolesUsuario.stream().map(p -> modelM.map(p, RolUsuarioDTO.class)).collect(Collectors.toList());
    }

    @PostMapping("/")
    public RolUsuarioDTO agregarRolUsuario(@RequestBody RolUsuario rolUsuario) {
        logger.info("RolUsuario a agregar: " + rolUsuario);
        var rolUsuarioGuardado = this.servicio.guardarRolUsuario(rolUsuario);
        return modelM.map(rolUsuarioGuardado, RolUsuarioDTO.class);
    }

    @GetMapping("/")
    public ResponseEntity<RolUsuarioDTO> obtenerRolUsuarioPorId(@RequestBody PK_RolUsuario id) {
        RolUsuario rolUsuario = servicio.buscarRolUsuarioPorId(id);
        if (rolUsuario != null) {
            return ResponseEntity.ok(modelM.map(rolUsuario, RolUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró el id: " + id);
    }

    @DeleteMapping("/")
    public ResponseEntity<Map<String, Boolean>> eliminarRolUsuario(@RequestBody PK_RolUsuario id) {
        var rolUsuario = servicio.buscarRolUsuarioPorId(id);
        servicio.eliminarRolUsuario(rolUsuario);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }


}
