package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.DireccionDTO;
import com.geovivienda.geovivienda.entities.Direccion;
import com.geovivienda.geovivienda.exceptions.LocationNotFoundException;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.externalapis.GeoapifyConnection;
import com.geovivienda.geovivienda.services.interfaces.IDireccionService;
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
@RequestMapping("geovivienda/direcciones")
public class DireccionController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IDireccionService servicio;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<DireccionDTO> obtenerDirecciones() {
        return servicio.listarDirecciones().stream().map(p -> modelM.map(p, DireccionDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public DireccionDTO agregarDireccion(@RequestParam String d) {
        DireccionDTO dto = null;
        try {
            dto = new GeoapifyConnection(d).getDireccionDTOAsociada();
        } catch (Exception e) {
            throw new LocationNotFoundException("No se encontró la dirección propuesta o el formato es incorrecto");
        }

        return modelM.map(this.servicio.guardarDireccion(modelM.map(dto, Direccion.class)), DireccionDTO.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DireccionDTO> modificarDireccion(@PathVariable int id, @RequestParam String d) {
        DireccionDTO dto = null;
        Direccion direccion = servicio.buscarDireccionPorId(id);
        try {
            dto = new GeoapifyConnection(d).getDireccionDTOAsociada();
            direccion.setDireccion(dto.getDireccion());
            direccion.setLatitud(dto.getLatitud());
            direccion.setLongitud(dto.getLongitud());
        } catch (Exception e) {
            throw new LocationNotFoundException("No se encontró la dirección propuesta o el formato es incorrecto");
        }

        return ResponseEntity.ok(modelM.map(servicio.guardarDireccion(direccion), DireccionDTO.class));
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> eliminarDireccion(@PathVariable int id) {
        var direccion = servicio.buscarDireccionPorId(id);
        servicio.eliminarDireccion(direccion);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }

}
