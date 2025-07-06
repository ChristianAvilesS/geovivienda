package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.AprobarCompraInmuebleDTO;
import com.geovivienda.geovivienda.dtos.FinalizarCompraInmuebleDTO;
import com.geovivienda.geovivienda.dtos.InmuebleUsuarioDTO;
import com.geovivienda.geovivienda.entities.Contrato;
import com.geovivienda.geovivienda.entities.InmuebleUsuario;
import com.geovivienda.geovivienda.entities.Pago;
import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/inmueblesusuario")
public class InmuebleUsuarioController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IInmuebleUsuarioService servicio;

    @Autowired
    private IInmuebleService inmuebleService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IPagoService pagoService;

    @Autowired
    private IContratoService contratoService;

    @Autowired
    private IMedioPagoService medioPagoService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<InmuebleUsuarioDTO> obtenerInmuebleUsuarios() {
        return servicio.listarInmuebleUsuarios().stream()
                .map(i-> modelM.map(i, InmuebleUsuarioDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public InmuebleUsuarioDTO agregarInmuebleUsuario(@RequestBody InmuebleUsuarioDTO dto) {
        var iu = modelM.map(dto, InmuebleUsuario.class);
        iu.setInmueble(inmuebleService.buscarInmueblePorId(dto.getInmueble().getIdInmueble()));
        iu.setUsuario(usuarioService.buscarUsuarioPorId(dto.getUsuario().getIdUsuario()));
        iu.setEsFavorito(dto.getEsFavorito());
        iu.setEsDuenio(dto.getEsDuenio());
        iu.setEstadoSolicitud(dto.getEstadoSolicitud());
        iu.setFechaSolicitud(dto.getFechaSolicitud());
        return modelM.map(servicio.guardarInmuebleUsuario(iu), InmuebleUsuarioDTO.class);
    }

    @GetMapping("/buscar")
    public ResponseEntity<InmuebleUsuarioDTO> obtenerInmuebleUsuarioPorId(
            @RequestParam int idInmueble,
            @RequestParam int idUsuario) {
        InmuebleUsuarioId id = new InmuebleUsuarioId();
        id.setIdInmueble(idInmueble);
        id.setIdUsuario(idUsuario);
        InmuebleUsuario inmuebleUsuario = servicio.buscarInmuebleUsuarioPorId(id);
        if (inmuebleUsuario != null) {
            return ResponseEntity.ok(modelM.map(inmuebleUsuario, InmuebleUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró el id: " + id);
    }


    @GetMapping("/duenio/{idInmueble}")
    public ResponseEntity<InmuebleUsuarioDTO> obtenerUsuarioDuenio(@PathVariable int idInmueble) {
        InmuebleUsuario inmuebleUsuario = servicio.findUsuarioDuenioByInmueble(idInmueble);
        if(inmuebleUsuario != null){
            return ResponseEntity.ok(modelM.map(inmuebleUsuario, InmuebleUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró el id: " + idInmueble);
    }

    @DeleteMapping
    public ResponseEntity<Map<String,Boolean>> eliminarInmuebleUsuario(@RequestBody InmuebleUsuarioId id) {
        var inmuebleUsuario = servicio.buscarInmuebleUsuarioPorId(id);
        servicio.eliminarInmuebleUsuario(inmuebleUsuario);
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/marcardesmarcarfavorito")
    public ResponseEntity<InmuebleUsuarioDTO> marcarDesmarcarFavorito(@RequestParam int idInmueble, @RequestParam int idUsuario) {
        InmuebleUsuarioId id = new InmuebleUsuarioId();
        id.setIdInmueble(idInmueble);
        id.setIdUsuario(idUsuario);

        // Buscar si ya existe la relación Inmueble-Usuario
        InmuebleUsuario existente = servicio.buscarInmuebleUsuarioPorId(id);

        if (existente == null) {
            // Crear relación nueva con estado inicial
            InmuebleUsuario nuevo = new InmuebleUsuario();
            nuevo.setId(id);
            nuevo.setUsuario(usuarioService.buscarUsuarioPorId(idUsuario));
            nuevo.setInmueble(inmuebleService.buscarInmueblePorId(idInmueble));
            nuevo.setEsFavorito(false);
            nuevo.setEsDuenio(false);
            nuevo.setEstadoSolicitud("NINGUNO");
            nuevo.setFechaSolicitud(LocalDate.now());
            servicio.guardarInmuebleUsuario(nuevo);
        }

        // Alternar favorito (crear si no existe o actualizar si existe)
        InmuebleUsuario actualizado = servicio.marcarDesmarcarFavorito(idInmueble, idUsuario);


        return ResponseEntity.ok(modelM.map(actualizado, InmuebleUsuarioDTO.class));
    }


    @PutMapping("/solicitarcomprainmueble")
    public ResponseEntity<InmuebleUsuarioDTO> solicitarCompraInmueble(@RequestParam int idInmueble,
                                                                      @RequestParam int idUsuario) {
        var inmuebleUsuario = servicio.solicitarCompraInmueble(idInmueble, idUsuario);
        if(inmuebleUsuario != null){
            return ResponseEntity.ok(modelM.map(inmuebleUsuario, InmuebleUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró la relación con idInmueble: " + idInmueble +
                " - idUsuario: " + idUsuario);
    }

    @PutMapping("/aprobarcomprainmueble")
    public ResponseEntity<InmuebleUsuarioDTO> aprobarCompraInmueble(@RequestBody AprobarCompraInmuebleDTO dto) {
        var inmuebleUsuario = servicio.aprobarCompraInmueble(dto.getIdInmueble(), dto.getIdUsuarioComprador());
        BigDecimal precioBaseInmueble = inmuebleService.buscarInmueblePorId(dto.getIdInmueble()).getPrecioBase();

        ZoneId zona = ZoneId.systemDefault(); // o ZoneId.of("America/Lima")

        if(inmuebleUsuario != null){
            var contrato = new Contrato();

            contrato.setDescripcion(dto.getDescripcionContrato());
            contrato.setTipoContrato(dto.getTipoContrato());
            contrato.setMontoTotal(precioBaseInmueble);
            contrato.setFechaFirma(Instant.now());
            contrato.setFechaVencimiento(dto.getFechaVencimientoContrato().atStartOfDay(zona).toInstant());
            contrato.setInmueble(inmuebleService.buscarInmueblePorId(dto.getIdInmueble()));
            contrato.setVendedor(usuarioService.buscarUsuarioPorId(dto.getIdUsuarioVendedor()));
            contrato.setComprador(usuarioService.buscarUsuarioPorId(dto.getIdUsuarioComprador()));

            contratoService.guardarContrato(contrato);

            return ResponseEntity.ok(modelM.map(inmuebleUsuario, InmuebleUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró la relación");
    }

    @PutMapping("/rechazarcomprainmueble")
    public ResponseEntity<InmuebleUsuarioDTO> rechazarCompraInmueble(@RequestParam int idInmueble,
                                                                    @RequestParam int idUsuario) {
        var inmuebleUsuario = servicio.rechazarCompraInmueble(idInmueble, idUsuario);
        if(inmuebleUsuario != null){
            return ResponseEntity.ok(modelM.map(inmuebleUsuario, InmuebleUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró la relación con idInmueble: " + idInmueble +
                " - idUsuario: " + idUsuario);
    }

    @PutMapping("/finalizarcomprainmueble")
    public ResponseEntity<InmuebleUsuarioDTO> finalizarCompraInmueble(@RequestBody FinalizarCompraInmuebleDTO dto) {
        var contrato = contratoService.buscarContratoPorId(dto.getIdContrato());
        var medioPago = medioPagoService.buscarMedioPagoPorId(dto.getIdMedioPago());

        ZoneId zona = ZoneId.systemDefault(); // o ZoneId.of("America/Lima")


        var inmuebleUsuario = servicio.finalizarCompraInmueble(contrato.getInmueble().getIdInmueble(),
                                                                contrato.getVendedor().getIdUsuario(),
                                                                contrato.getComprador().getIdUsuario());

        if(inmuebleUsuario != null){
            var pago = new Pago();

            pago.setContrato(contrato);
            pago.setDescripcion(dto.getDescripcionPago());
            pago.setTipoMoneda(dto.getTipoMoneda());
            pago.setFechaPago(Instant.now());
            pago.setImporte(dto.getImporte());
            pago.setFechaVencimiento(dto.getFechaVencimientoPago().atStartOfDay(zona).toInstant());
            pago.setMedio(medioPago);
            pago.setIdPago(null);

            pagoService.guardarPago(pago);

            return ResponseEntity.ok(modelM.map(inmuebleUsuario, InmuebleUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró la relación");
    }

    @GetMapping("/listarSolicitados/{idUsuario}")
    public List<InmuebleUsuarioDTO> listarEstadoSolicitadoPorIdUsuarioVendedor(@PathVariable int idUsuario) {
        return servicio.listarEstadoSolicitadoPorIdUsuarioVendedor(idUsuario).stream()
                .map(i-> modelM.map(i, InmuebleUsuarioDTO.class)).collect(Collectors.toList());
    }
}
