package com.geovivienda.geovivienda.controllers;


import com.geovivienda.geovivienda.dtos.*;
import com.geovivienda.geovivienda.entities.Valoracion;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleService;
import com.geovivienda.geovivienda.services.interfaces.IUsuarioService;
import com.geovivienda.geovivienda.services.interfaces.IValoracionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Autowired
    private IUsuarioService userService;

    @Autowired
    private IInmuebleService inmuebleService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('COMPRADOR', 'ADMIN')")
    public List<ValoracionDTO> listarValoraciones() {
        return servicio.listarValoraciones().stream()
                .map(v -> modelM.map(v, ValoracionDTO.class)).collect(Collectors.toList());
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('COMPRADOR', 'ADMIN')")
    public ValoracionDTO agregarValoracion(@RequestBody ValoracionDTO dto){
        var valoracion = modelM.map(dto, Valoracion.class);
        valoracion.setIdValoracion(null);
        valoracion.setInmueble(inmuebleService.buscarInmueblePorId(dto.getInmueble().getIdInmueble()));
        valoracion.setUsuario(userService.buscarUsuarioPorId(dto.getUsuario().getIdUsuario()));
        return modelM.map(this.servicio.guardarValoracion(valoracion), ValoracionDTO.class);
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
    @PreAuthorize("hasAnyAuthority('COMPRADOR', 'ADMIN')")
    public ResponseEntity<Map<String,Boolean>> eliminarValoracion(@PathVariable int id) {
        var valoracion = servicio.buscarValoracionPorId(id);
        servicio.eliminarValoracion(valoracion);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }





    @GetMapping("/cantidadXInmueble")
    @PreAuthorize("hasAnyAuthority('COMPRADOR', 'ARRENDATARIO', 'ADMIN')")
    public List<ValoracionXInmuebleDTO> obtenerCantidadXInmueble() {
        List<ValoracionXInmuebleDTO> dtoLista = new ArrayList<>();
        List<String[]> filaLista = servicio.cantidadValoracionesXInmueble();
        for (String[] columna : filaLista) {
            ValoracionXInmuebleDTO dto = new ValoracionXInmuebleDTO();
            dto.setNombreInmueble(columna[0]);
            dto.setRating(Long.parseLong(columna[1]));
            dtoLista.add(dto);
        }
        return dtoLista;

    }


    @GetMapping("/promediovaloracion")
    public List<ValoracionInmuebleDTO> obtenerValoracion() {
        List<ValoracionInmuebleDTO> dtoLista = new ArrayList<>();
        List<String[]> filaLista = servicio.valoracionInmueble();
        for (String[] columna : filaLista) {
            ValoracionInmuebleDTO dto = new ValoracionInmuebleDTO();
            dto.setNombreInmueble(columna[0]);
            dto.setValoracion(new BigDecimal(columna[1]));
            dtoLista.add(dto);
        }
        return dtoLista;

    }

}
