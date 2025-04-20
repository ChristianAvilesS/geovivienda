package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.RolDTO;
import com.geovivienda.geovivienda.entities.Rol;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IRolService;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IRolService servicio;

    @GetMapping
    public List<RolDTO> obtenerRoles() {
        return servicio.listarRoles().stream().map(p -> modelM.map(p, RolDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public RolDTO agregarRol(@RequestBody RolDTO dto) {
        return modelM.map(this.servicio.guardarRol(modelM.map(dto, Rol.class)), RolDTO.class);
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
    public ResponseEntity<RolDTO> actualizarRol(@PathVariable int id, @RequestBody RolDTO dto) {
        Rol rol = this.servicio.buscarRolPorId(id);
        rol.setRol(dto.getRol());
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

    @GetMapping("/")
    public RolDTO buscarRolPorNombre(@RequestParam String nombre) {
        return modelM.map(this.servicio.buscarRolPorNombre(nombre), RolDTO.class);
    }


}
