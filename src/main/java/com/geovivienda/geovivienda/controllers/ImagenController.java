package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.CantidadImagenesXInmuebleDTO;
import com.geovivienda.geovivienda.dtos.ImagenDTO;
import com.geovivienda.geovivienda.entities.Imagen;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IImagenService;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private IInmuebleService inService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ImagenDTO> obtenerImagenes() {
        return servicio.listarImagenes().stream()
                .map(p -> modelM.map(p, ImagenDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('VENDEDOR')")
    public ImagenDTO agregarImagen(@RequestBody ImagenDTO dto) {
        Imagen imagen = modelM.map(dto, Imagen.class);
        imagen.setInmueble(inService.buscarInmueblePorId(dto.getInmueble().getIdInmueble()));

        return modelM.map(this.servicio.guardarImagen(imagen), ImagenDTO.class);
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
    @PreAuthorize("hasAnyRole('VENDEDOR', 'ADMIN')")
    public ResponseEntity<Map<String, Boolean>> eliminarImagen(@PathVariable int id) {
        var imagen = servicio.buscarImagenPorId(id);
        servicio.eliminarImagen(imagen);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/cantidad")
    public List<CantidadImagenesXInmuebleDTO> obtenerCantidadImagenesXInmueble() {
        List<CantidadImagenesXInmuebleDTO> dtoLista = new ArrayList<>();
        List<String[]> filaLista = servicio.cantidadImagenesXInmueble();
        for (String[] columna : filaLista) {
            CantidadImagenesXInmuebleDTO dto = new CantidadImagenesXInmuebleDTO();
            dto.setNombreInmueble(columna[0]);
            dto.setCantidadImagenes(Integer.parseInt(columna[2]));
            dtoLista.add(dto);
        }
        return dtoLista;

    }
}
