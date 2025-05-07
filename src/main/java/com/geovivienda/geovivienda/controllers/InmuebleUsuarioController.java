package com.geovivienda.geovivienda.controllers;

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
        return modelM.map(servicio.guardarInmuebleUsuario(iu), InmuebleUsuarioDTO.class);
    }

    @GetMapping("/buscar")
    public ResponseEntity<InmuebleUsuarioDTO> obtenerInmuebleUsuarioPorId(@RequestBody InmuebleUsuarioId id) {
        InmuebleUsuario inmuebleUsuario = servicio.buscarInmuebleUsuarioPorId(id);
        if(inmuebleUsuario != null){
            return ResponseEntity.ok(modelM.map(inmuebleUsuario, InmuebleUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró el id: " + id);
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
    public ResponseEntity<Map<String,Boolean>> marcarDesmarcarFavorito(@RequestParam int idInmueble, @RequestParam int idUsuario) {
        var inmuebleUsuario = servicio.marcarDesmarcarFavorito(idInmueble, idUsuario);
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("esFavorito", inmuebleUsuario.getEsFavorito());
        return ResponseEntity.ok(respuesta);
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
    public ResponseEntity<InmuebleUsuarioDTO> aprobarCompraInmueble(@RequestParam int idInmueble,
                                                                    @RequestParam int idUsuarioComprador,
                                                                    @RequestParam int idUsuarioVendedor,
                                                                    @RequestParam String descripcionContrato,
                                                                    @RequestParam String tipoContrato,
                                                                    @RequestParam LocalDate fechaVencimientoContrato) {
        var inmuebleUsuario = servicio.aprobarCompraInmueble(idInmueble, idUsuarioComprador);
        BigDecimal precioBaseInmueble = inmuebleService.buscarInmueblePorId(idInmueble).getPrecioBase();

        ZoneId zona = ZoneId.systemDefault(); // o ZoneId.of("America/Lima")

        if(inmuebleUsuario != null){
            var contrato = new Contrato();

            contrato.setDescripcion(descripcionContrato);
            contrato.setTipoContrato(tipoContrato);
            contrato.setMontoTotal(precioBaseInmueble);
            contrato.setFechaFirma(Instant.now());
            contrato.setFechaVencimiento(fechaVencimientoContrato.atStartOfDay(zona).toInstant());
            contrato.setInmueble(inmuebleService.buscarInmueblePorId(idInmueble));
            contrato.setVendedor(usuarioService.buscarUsuarioPorId(idUsuarioVendedor));
            contrato.setComprador(usuarioService.buscarUsuarioPorId(idUsuarioComprador));

            contratoService.guardarContrato(contrato);

            return ResponseEntity.ok(modelM.map(inmuebleUsuario, InmuebleUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró la relación con idInmueble: " + idInmueble +
                " - idUsuario: " + idUsuarioComprador);
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
    public ResponseEntity<InmuebleUsuarioDTO> finalizarCompraInmueble(@RequestParam int idContrato,
                                                                        @RequestParam int idMedioPago,
                                                                        @RequestParam String descripcionPago,
                                                                        @RequestParam String tipoMoneda,
                                                                        @RequestParam BigDecimal importe,
                                                                        @RequestParam LocalDate fechaVencimientoPago) {
        var contrato = contratoService.buscarContratoPorId(idContrato);
        var medioPago = medioPagoService.buscarMedioPagoPorId(idMedioPago);

        ZoneId zona = ZoneId.systemDefault(); // o ZoneId.of("America/Lima")


        var inmuebleUsuario = servicio.finalizarCompraInmueble(contrato.getInmueble().getIdInmueble(),
                                                                contrato.getVendedor().getIdUsuario(),
                                                                contrato.getComprador().getIdUsuario());

        if(inmuebleUsuario != null){
            var pago = new Pago();

            pago.setContrato(contrato);
            pago.setDescripcion(descripcionPago);
            pago.setTipoMoneda(tipoMoneda);
            pago.setFechaPago(Instant.now());
            pago.setImporte(importe);
            pago.setFechaVencimiento(fechaVencimientoPago.atStartOfDay(zona).toInstant());
            pago.setMedio(medioPago);

            pagoService.guardarPago(pago);

            return ResponseEntity.ok(modelM.map(inmuebleUsuario, InmuebleUsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró la relación");
    }
}
