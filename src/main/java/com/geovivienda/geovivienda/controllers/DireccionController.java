package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.DireccionDTO;
import com.geovivienda.geovivienda.entities.Direccion;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IDireccionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/direcciones")
public class DireccionController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IDireccionService servicio;

    @GetMapping
    public List<DireccionDTO> obtenerDireccions() {
        return servicio.listarDirecciones().stream().map(p -> modelM.map(p, DireccionDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public DireccionDTO agregarDireccion(@RequestBody DireccionDTO dto) {
        return modelM.map(this.servicio.guardarDireccion(modelM.map(dto, Direccion.class)), DireccionDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DireccionDTO> obtenerDireccionPorId(@PathVariable int id) {
        Direccion direccion = servicio.buscarDireccionPorId(id);
        if (direccion != null) {
            return ResponseEntity.ok(modelM.map(direccion, DireccionDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró el id: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarDireccion(@PathVariable int id) {
        var direccion = servicio.buscarDireccionPorId(id);
        servicio.eliminarDireccion(direccion);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/buscar")
    public List<DireccionDTO> obtenerDireccionesEnRango(@RequestBody DireccionDTO dto, @RequestParam("rango") BigDecimal rango) {
        return servicio.buscarDireccionesEnRango(modelM.map(dto, Direccion.class), rango).stream()
                .map(p -> modelM.map(p, DireccionDTO.class)).collect(Collectors.toList());
    }

}
