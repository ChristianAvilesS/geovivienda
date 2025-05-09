package com.geovivienda.geovivienda.controllers;


import com.geovivienda.geovivienda.dtos.ValoracionDTO;
import com.geovivienda.geovivienda.entities.Valoracion;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IValoracionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/geovivienda/valoracion")
public class ValoracionController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IValoracionService servicio;

    @GetMapping
    public List<ValoracionDTO> listarValoraciones() {
        return servicio.listarValoraciones().stream()
                .map(v -> modelM.map(v, ValoracionDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public ValoracionDTO agregarValoracion(@RequestBody ValoracionDTO dto){
        return modelM.map(servicio.guardarValoracion(modelM.map(dto, Valoracion.class)), ValoracionDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValoracionDTO> obtenerValoracionPorId(@PathVariable int id) {
        Valoracion valoracion = servicio.buscarValoracionPorId(id);
        if(valoracion != null){
            return ResponseEntity.ok(modelM.map(valoracion, ValoracionDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontro el contrato con el id: "+id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Boolean>> eliminarContrato(@PathVariable int id) {
        var contrato = servicio.buscarValoracionPorId(id);
        servicio.eliminarValoracion(contrato);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }
}
