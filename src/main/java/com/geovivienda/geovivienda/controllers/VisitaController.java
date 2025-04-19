package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.VisitaDTO;
import com.geovivienda.geovivienda.entities.Visita;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IVisitaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/visitas")
public class VisitaController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IVisitaService servicio;

    @GetMapping
    public List<VisitaDTO> obtenerVisitas() {
        return servicio.listarVisitas().stream()
                .map(p -> modelM.map(p, VisitaDTO.class)).collect(Collectors.toList());
    }
    @PostMapping
    public VisitaDTO agregarVisita(@RequestBody VisitaDTO dto) {
        return modelM.map(this.servicio.guardarVisita(modelM.map(dto, Visita.class)),VisitaDTO.class);

    }
    @GetMapping("/{id}")
    public ResponseEntity<VisitaDTO> obtenerVisitaPorId(@PathVariable int id) {
        Visita visita = servicio.buscarVisitaPorId(id);

        if (visita != null) {
            return ResponseEntity.ok(modelM.map(visita, VisitaDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontro el id: " + id);
    }
}






