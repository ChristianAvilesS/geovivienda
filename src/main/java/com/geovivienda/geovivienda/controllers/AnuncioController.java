package com.geovivienda.geovivienda.controllers;


import com.geovivienda.geovivienda.dtos.AnuncioDTO;
import com.geovivienda.geovivienda.dtos.CantidadAnunciosXUsuarioDTO;
import com.geovivienda.geovivienda.entities.Anuncio;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IAnuncioService;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleService;
import com.geovivienda.geovivienda.services.interfaces.IUsuarioService;
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
@RequestMapping("geovivienda/anuncios")
public class AnuncioController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IAnuncioService servicio;

    @Autowired
    private IUsuarioService userService;

    @Autowired
    private IInmuebleService inmuebleService;

    @GetMapping
    public List<AnuncioDTO> obtenerAnuncios() {
        return servicio.listarAnuncios().stream()
                .map(p -> modelM.map(p, AnuncioDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('VENDEDOR', 'ADMIN')")
    public AnuncioDTO agregarAnuncio(@RequestBody AnuncioDTO dto) {
        var anuncio = modelM.map(dto, Anuncio.class);
        anuncio.setAnunciante(userService.buscarUsuarioPorId(dto.getAnunciante().getIdUsuario()));
        anuncio.setInmueble(inmuebleService.buscarInmueblePorId(dto.getInmueble().getIdInmueble()));
        return modelM.map(this.servicio.guardarAnuncio(anuncio), AnuncioDTO.class);
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> eliminarAnuncio(@PathVariable int id) {
        var anuncio = servicio.buscarAnuncioPorId(id);
        servicio.eliminarAnuncio(anuncio);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/cantidad")
    @PreAuthorize("hasAnyAuthority('VENDEDOR', 'ADMIN')")
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
