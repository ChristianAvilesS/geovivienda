package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.DireccionDTO;
import com.geovivienda.geovivienda.entities.Direccion;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IDireccionService;
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
@RequestMapping("geovivienda/direcciones")
public class DireccionController {
    private static final Logger logger = LoggerFactory.getLogger(DireccionController.class);
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IDireccionService servicio;

    @GetMapping("/")
    public List<DireccionDTO> obtenerDireccions() {
        var direcciones = servicio.listarDirecciones();
        direcciones.forEach((direccion) -> logger.info(direccion.toString()));
        return direcciones.stream().map(p -> modelM.map(p, DireccionDTO.class)).collect(Collectors.toList());
    }

    @PostMapping("/")
    public DireccionDTO agregarDireccion(@RequestBody Direccion direccion) {
        logger.info("Direccion a agregar: " + direccion);
        var direccionGuardada = this.servicio.guardarDireccion(direccion);
        return modelM.map(direccionGuardada, DireccionDTO.class);
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


}
