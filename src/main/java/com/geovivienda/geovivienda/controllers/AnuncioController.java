package com.geovivienda.geovivienda.controllers;


import com.geovivienda.geovivienda.dtos.AnuncioDTO;
import com.geovivienda.geovivienda.dtos.CantidadAnunciosXUsuarioDTO;
import com.geovivienda.geovivienda.entities.Anuncio;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IAnuncioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/anuncios")
public class AnuncioController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IAnuncioService servicio;

    @GetMapping
    public List<AnuncioDTO> obtenerAnuncios() {
        return servicio.listarAnuncios().stream()
                .map(p -> modelM.map(p, AnuncioDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public AnuncioDTO agregarAnuncio(@RequestBody AnuncioDTO dto) {
        return modelM.map(this.servicio.guardarAnuncio(modelM.map(dto, Anuncio.class)), AnuncioDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnuncioDTO> obtenerAnuncioPorId(@PathVariable int id) {
        Anuncio anuncio = servicio.buscarAnuncioPorId(id);
        if (anuncio != null) {
            return ResponseEntity.ok(modelM.map(anuncio, AnuncioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró el id: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarAnuncio(@PathVariable int id) {
        var anuncio = servicio.buscarAnuncioPorId(id);
        servicio.eliminarAnuncio(anuncio);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/cantidad")
    public List<CantidadAnunciosXUsuarioDTO> obtenerCantidadAnunciosXUsuario() {
        List<CantidadAnunciosXUsuarioDTO> dtoLista = new ArrayList<>();
        List<String[]> filaLista = servicio.cantidadAnunciosXUsuario();
        for (String[] columna : filaLista) {
            CantidadAnunciosXUsuarioDTO dto = new CantidadAnunciosXUsuarioDTO();
            dto.setNombreUsuario(columna[0]);
            dto.setCantidadAnuncios(Integer.parseInt(columna[2]));
            dtoLista.add(dto);
        }
        return dtoLista;

    }



}
