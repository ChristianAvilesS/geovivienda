package com.geovivienda.geovivienda.controllers;


import com.geovivienda.geovivienda.dtos.AnuncioDTO;
import com.geovivienda.geovivienda.dtos.CantidadAnunciosXUsuarioDTO;
import com.geovivienda.geovivienda.dtos.ComentarioDTO;
import com.geovivienda.geovivienda.entities.Anuncio;
import com.geovivienda.geovivienda.entities.Comentario;
import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.entities.Usuario;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IAnuncioService;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleService;
import com.geovivienda.geovivienda.services.interfaces.IUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
                .sorted(Comparator.comparing(Anuncio::getFechaPublicacion).reversed())
                .limit(20)
                .map(p -> modelM.map(p, AnuncioDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('VENDEDOR', 'ADMIN')")
    public AnuncioDTO agregarAnuncio(@RequestBody AnuncioDTO dto) {
        var anuncio = modelM.map(dto, Anuncio.class);
        anuncio.setIdAnuncio(null);
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

    @PutMapping
    public Anuncio editarAnuncio(@RequestBody AnuncioDTO dto) {
        Inmueble inmueble = inmuebleService.buscarInmueblePorId(dto.getInmueble().getIdInmueble());
        Usuario usuario = userService.buscarUsuarioPorId(dto.getAnunciante().getIdUsuario());

        Anuncio anuncio = new Anuncio();
        anuncio.setIdAnuncio(dto.getIdAnuncio());
        anuncio.setDescripcion(dto.getDescripcion());
        anuncio.setInmueble(inmueble);
        anuncio.setAnunciante(usuario);
        anuncio.setFechaPublicacion(dto.getFechaPublicacion());

        return servicio.actualizarAnuncio(anuncio);
    }

    @GetMapping("/buscarporinmueble")
    public ResponseEntity<AnuncioDTO> buscarPorInmueble(@RequestParam int idInmueble) {
        Anuncio anuncio = servicio.buscarPorInmueble(idInmueble);
        AnuncioDTO dto = modelM.map(anuncio, AnuncioDTO.class);
        return ResponseEntity.ok(dto);
    }

}
