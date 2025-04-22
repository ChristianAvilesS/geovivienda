package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.ImagenDTO;
import com.geovivienda.geovivienda.entities.Imagen;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IImagenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/imagenes")
public class ImagenController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IImagenService servicio;

    @GetMapping
    public List<ImagenDTO> obtenerImagenes() {
        return servicio.listarImagenes().stream()
                .map(p -> modelM.map(p, ImagenDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public ImagenDTO agregarImagen(@RequestBody ImagenDTO dto) {
        return modelM.map(this.servicio.guardarImagen(modelM.map(dto, Imagen.class)), ImagenDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagenDTO> obtenerImagenPorId(@PathVariable int id) {
        Imagen imagen = servicio.buscarImagenPorId(id);
        if (imagen != null) {
            return ResponseEntity.ok(modelM.map(imagen, ImagenDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró el id: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarImagen(@PathVariable int id) {
        var imagen = servicio.buscarImagenPorId(id);
        servicio.eliminarImagen(imagen);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }
}
