package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.VisitaDTO;
import com.geovivienda.geovivienda.entities.Visita;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleService;
import com.geovivienda.geovivienda.services.interfaces.IUsuarioService;
import com.geovivienda.geovivienda.services.interfaces.IVisitaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/visitas")
public class VisitaController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IVisitaService servicio;

    @Autowired
    private IUsuarioService userService;

    @Autowired
    private IInmuebleService inmuebleService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<VisitaDTO> obtenerVisitas() {
        return servicio.listarVisitas().stream()
                .map(p -> modelM.map(p, VisitaDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('COMPRADOR', 'ARRENDATARIO')")
    public VisitaDTO agregarVisita(@RequestBody VisitaDTO dto) {
        var visita = modelM.map(dto, Visita.class);
        visita.setInmueble(inmuebleService.buscarInmueblePorId(dto.getInmueble().getIdInmueble()));
        visita.setUsuario(userService.buscarUsuarioPorId(dto.getUsuario().getIdUsuario()));
        return modelM.map(this.servicio.guardarVisita(visita),VisitaDTO.class);

    }
    @GetMapping("/{id}")
    public ResponseEntity<VisitaDTO> obtenerVisitaPorId(@PathVariable int id) {
        Visita visita = servicio.buscarVisitaPorId(id);

        if (visita != null) {
            return ResponseEntity.ok(modelM.map(visita, VisitaDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontro el id: " + id);
    }

    @GetMapping("/buscarporfecha/{id}")
    @PreAuthorize("hasAuthority('VENDEDOR')")
    public List<VisitaDTO> buscarVisitaPorInmuebleYFecha(@RequestParam("fecha") Instant fecha,
                                                         @PathVariable Integer id) {
        return servicio.buscarVisitaPorInmuebleYFecha(fecha, id).stream()
                .map(p -> modelM.map(p, VisitaDTO.class)).collect(Collectors.toList());
    }
}






