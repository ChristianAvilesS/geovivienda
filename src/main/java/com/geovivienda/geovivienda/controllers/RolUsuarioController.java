package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.RolPredominanteDTO;
import com.geovivienda.geovivienda.dtos.RolUsuarioDTO;
import com.geovivienda.geovivienda.entities.RolUsuario;
import com.geovivienda.geovivienda.entities.ids.RolUsuarioId;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IRolUsuarioService;
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
@RequestMapping("geovivienda/rolesusuario")
public class RolUsuarioController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IRolUsuarioService servicio;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<RolUsuarioDTO> obtenerRolUsuarios() {
        return servicio.listarRolesUsuario().stream()
                .map(p -> modelM.map(p, RolUsuarioDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public RolUsuarioDTO agregarRolUsuario(@RequestBody RolUsuarioDTO dto) {
        RolUsuario rolUsuario = new RolUsuario();
        rolUsuario.setId(dto.getIdRolUsuario());

        return modelM.map(servicio.guardarRolUsuario(rolUsuario), RolUsuarioDTO.class);
    }

    @GetMapping("/buscar")
    public ResponseEntity<RolUsuarioDTO> obtenerRolUsuarioPorId(@RequestBody RolUsuarioId id) {
        RolUsuario rolUsuario = servicio.buscarRolUsuarioPorId(id);
        if (rolUsuario != null) {
            return ResponseEntity.ok(modelM.map(rolUsuario, RolUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró el id: " + id);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Boolean>> eliminarRolUsuario(@RequestBody RolUsuarioId id) {
        var rolUsuario = servicio.buscarRolUsuarioPorId(id);
        servicio.eliminarRolUsuario(rolUsuario);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/buscar/{id}")
    public List<RolUsuarioDTO> obtenerRolesUsuarioPorId(@PathVariable int id) {
        return servicio.buscarRolesPorUsuario(id).stream().map(ru -> modelM.map(ru, RolUsuarioDTO.class)
        ).collect(Collectors.toList());
    }

    @GetMapping("/rol_predom/{id}")
    public RolPredominanteDTO obtenerRolPredominantePorUsuario(@PathVariable int id) {
        return servicio.findPredominantUserRol(id).stream().map(r -> {
            RolPredominanteDTO dto = new RolPredominanteDTO();
            dto.setIdUsuario(id);
            dto.setRol(r[1]);
            return dto;
        }).toList().get(0);
    }

}
