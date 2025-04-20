package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.DireccionDTO;
import com.geovivienda.geovivienda.dtos.InmuebleDTO;
import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleService;
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
@RequestMapping("geovivienda/inmuebles")
public class InmuebleController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IInmuebleService servicio;

    @GetMapping
    public List<InmuebleDTO> obtenerInmuebles(){
        return servicio.listarInmuebles().stream()
                .map(p -> modelM.map(p,InmuebleDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public InmuebleDTO agregarInmueble(@RequestBody InmuebleDTO dto) {
        return modelM.map(servicio.guardarInmueble(modelM.map(dto, Inmueble.class)), InmuebleDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InmuebleDTO> obtenerInmueblePorId(@PathVariable int id){
        Inmueble inmueble = servicio.buscarInmueblePorId(id);
        if(inmueble != null){
            return ResponseEntity.ok(modelM.map(inmueble, InmuebleDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontro el inmueble con el id "+id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Boolean>> eliminarInmueble(@PathVariable int id){
        var inmueble = servicio.buscarInmueblePorId(id);
        servicio.eliminarInmueble(inmueble);
        Map<String, Boolean>respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }

}

