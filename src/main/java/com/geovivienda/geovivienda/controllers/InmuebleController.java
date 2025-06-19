package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.DireccionDTO;
import com.geovivienda.geovivienda.dtos.InmuebleDTO;
import com.geovivienda.geovivienda.dtos.InmuebleDireccionDTO;
import com.geovivienda.geovivienda.entities.Direccion;
import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.exceptions.LocationNotFoundException;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.externalapis.GeoapifyConnection;
import com.geovivienda.geovivienda.services.interfaces.IDireccionService;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("geovivienda/inmuebles")
public class InmuebleController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IInmuebleService servicio;

    @Autowired
    private IDireccionService dirService;

    @GetMapping
    public List<InmuebleDTO> obtenerInmuebles() {
        log.info("\nInicio de recuperación de inmuebles\n");

        var inmuebles = servicio.listarInmuebles().stream().map(p -> modelM.map(p, InmuebleDTO.class))
                .collect(Collectors.toList());

        log.info("\nInmuebles:");
        inmuebles.forEach(p -> log.info("{}", p));

        log.info("\nFin de recuperación de inmuebles\n");
        return inmuebles;
    }

    @GetMapping("/listado-logico")
    public List<InmuebleDTO> obtenerInmueblesNoEliminados() {
        log.info("\nInicio de recuperación de inmuebles\n");

        var inmuebles = servicio.listarNoEliminados().stream().map(p -> modelM.map(p, InmuebleDTO.class))
                .collect(Collectors.toList());

        log.info("\nInmuebles:");
        inmuebles.forEach(p -> log.info("{}", p));

        log.info("\nFin de recuperación de inmuebles\n");
        return inmuebles;
    }

    @PostMapping
//@PreAuthorize("hasAnyAuthority('VENDEDOR', 'ADMIN')")
    public InmuebleDTO agregarInmueble(@RequestBody InmuebleDTO dto) {
        log.info("\nInicio de registro de inmueble\n");
        log.info("Inmueble obtenido: {}", dto);
        if (dto.getIdInmueble() != null && dto.getIdInmueble() == 0) {
            dto.setIdInmueble(null); // CORRECCIÓN CLAVE
        }
        var inmueble = modelM.map(dto, Inmueble.class);
        if (dto.getDireccion().getIdDireccion() != null && dto.getDireccion().getIdDireccion() != 0) {
            inmueble.setDireccion(dirService.buscarDireccionPorId(dto.getDireccion().getIdDireccion()));
        } else {
            DireccionDTO dtoDir = getDireccionFromAPI(dto.getDireccion().getDireccion());
            inmueble.setDireccion(dirService.guardarDireccion(modelM.map(dtoDir, Direccion.class)));
        }
        var dtoTemp = modelM.map(servicio.guardarInmueble(inmueble), InmuebleDTO.class);
        log.info("Inmueble guardado: {}", dtoTemp);
        log.info("\nFin de registro de inmueble\n");
        return dtoTemp;
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('VENDEDOR', 'ADMIN')")
    public InmuebleDTO editarInmueble(@RequestBody InmuebleDTO dto) {
        log.info("\nInicio de modificación de inmueble\n");
        log.info("Inmueble obtenido: {}", dto);
        var inmueble = modelM.map(dto, Inmueble.class);
        if (dto.getDireccion().getIdDireccion() != null && dto.getDireccion().getIdDireccion() != 0) {
            inmueble.setDireccion(dirService.buscarDireccionPorId(dto.getDireccion().getIdDireccion()));
        } else {
            DireccionDTO dtoDir = getDireccionFromAPI(dto.getDireccion().getDireccion());
            inmueble.setDireccion(dirService.guardarDireccion(modelM.map(dtoDir, Direccion.class)));
        }
        var dtoTemp = modelM.map(servicio.editarInmueble(inmueble), InmuebleDTO.class);
        log.info("Inmueble editado: {}", dtoTemp);
        log.info("\nFin de modificación de inmueble\n");
        return dtoTemp;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InmuebleDTO> obtenerInmueblePorId(@PathVariable int id) {
        log.info("\nInicio de búsqueda de inmueble\n");
        Inmueble inmueble = servicio.buscarInmueblePorId(id);
        if (inmueble != null) {
            log.info("\nInmueble encontrado: {}", modelM.map(inmueble, InmuebleDTO.class));
            return ResponseEntity.ok(modelM.map(inmueble, InmuebleDTO.class));
        }

        log.info("Error al buscar el inmueble, id no encontrado");
        throw new RecursoNoEncontradoException("No se encontró el inmueble con el id " + id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('VENDEDOR', 'ADMIN')")
    public ResponseEntity<Map<String, Boolean>> eliminarInmueble(@PathVariable int id) {
        log.info("\nInicio de eliminación de inmueble\n");
        var inmueble = servicio.buscarInmueblePorId(id);
        servicio.eliminarInmueble(inmueble);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        log.info("Inmueble eliminado: {}", inmueble);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/inmuebles_en_direccion")
    public List<InmuebleDireccionDTO> obtenerInmueblesCercaADireccion(@RequestParam("dir") String dir,
                                                                      @RequestParam("rango") BigDecimal rango) {
        log.info("\nInicio de query de búsqueda de inmuebles cerca a una dirección inmueble\n");
        DireccionDTO direccion = getDireccionFromAPI(dir);
        var resultado = servicio.buscarInmueblesEnLugarEnRango(direccion.getLongitud(),
                        direccion.getLatitud(), rango).stream().map(this::createInmuebleDireccionDTO).toList();
        log.info("Resultado:");
        resultado.forEach(i -> log.info("{}", i));
        return resultado;
    }

    @GetMapping("/inmuebles_cerca_usuario")
    public List<InmuebleDireccionDTO> obtenerInmueblesCercaAUsuario(@RequestParam("lon") BigDecimal longitud,
                                                                    @RequestParam("lat") BigDecimal latitud) {
        log.info("\nInicio de query de búsqueda de inmuebles cerca al usuario\n");
        BigDecimal rango = BigDecimal.valueOf(0.01); // Radio de 1.1 km aprox

        var resultado = servicio.buscarInmueblesEnLugarEnRango(longitud, latitud, rango).stream()
                .map(this::createInmuebleDireccionDTO).toList();
        log.info("Resultado:");
        resultado.forEach(i -> log.info("{}", i));
        return resultado;
    }

    @GetMapping("/filtrado")
    public List<InmuebleDireccionDTO> filtrarInmueblesRangoArea(@RequestParam(value = "minArea", required = false)
                                                                BigDecimal minArea,
                                                                @RequestParam(value = "maxArea", required = false)
                                                                BigDecimal maxArea,
                                                                @RequestParam(value = "minPrecio", required = false)
                                                                BigDecimal minPrecio,
                                                                @RequestParam(value = "maxPrecio", required = false)
                                                                BigDecimal maxPrecio,
                                                                @RequestParam("latitud") BigDecimal latitud,
                                                                @RequestParam("longitud") BigDecimal longitud,
                                                                @RequestParam("radio") BigDecimal radio,
                                                                @RequestParam(value = "tipo", required = false)
                                                                String tipo) {

        log.info("\nInicio de query de filtrado de inmuebles\n");

        var resultado = servicio.filtrarInmuebles(minArea, maxArea, minPrecio, maxPrecio, latitud, longitud, radio, tipo)
                .stream().map(this::createInmuebleDireccionDTO).toList();
        log.info("Resultado:");
        resultado.forEach(i -> log.info("{}", i));
        return resultado;
    }

    @GetMapping("/favoritos")
    public List<InmuebleDireccionDTO> mostrarFavoritos(@RequestParam("idUsuario") int idUsuario) {
        log.info("\nInicio de query de inmuebles favoritos\n");
        var resultado = servicio.listarFavoritosPorUsuario(idUsuario)
                .stream()
                .map(this::createInmuebleDireccionDTO).toList();
        log.info("Resultado:");
        resultado.forEach(i -> log.info("{}", i));
        return resultado;
    }

    private InmuebleDireccionDTO createInmuebleDireccionDTO(Inmueble i) {
        var dto = new InmuebleDireccionDTO();
        dto.setNombre(i.getNombre());
        dto.setDireccion(i.getDireccion().getDireccion());
        dto.setArea(i.getArea());
        dto.setDescripcion(i.getDescripcion());
        dto.setTipo(i.getTipo());
        dto.setPrecioBase(i.getPrecioBase());
        return dto;
    }

    private DireccionDTO getDireccionFromAPI(String d) {
        DireccionDTO dtoDir;
        try {
            log.info("Estableciendo conexión con el API de Geoapify");
            dtoDir = new GeoapifyConnection(d).getDireccionDTOAsociada();
            log.info("Dirección recuperada: {}", dtoDir);
            return dtoDir;
        } catch (Exception e) {
            log.error("La dirección no pudo ser recuperada, info en el API Geoapify");
            throw new LocationNotFoundException("No se encontró la dirección propuesta o el formato es incorrecto");
        }
    }

}

