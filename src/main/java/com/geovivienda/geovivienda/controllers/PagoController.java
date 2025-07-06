package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.ImportePorMonedaDTO;
import com.geovivienda.geovivienda.dtos.MedioPagoDTO;
import com.geovivienda.geovivienda.dtos.PagoDTO;
import com.geovivienda.geovivienda.dtos.PagosCercanosDTO;
import com.geovivienda.geovivienda.entities.Contrato;
import com.geovivienda.geovivienda.entities.MedioPago;
import com.geovivienda.geovivienda.entities.Pago;
import com.geovivienda.geovivienda.services.interfaces.IContratoService;
import com.geovivienda.geovivienda.services.interfaces.IPagoService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/pagos")
public class PagoController {
    private final ModelMapper modelM = new ModelMapper();
    @Autowired
    private IPagoService servicio;

    @Autowired
    private IContratoService contratoService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<PagoDTO> obtenerPagos() { // Listar
        return servicio.listarPagos().stream().map(p -> modelM.map(p, PagoDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public PagoDTO agregarPago(@RequestBody PagoDTO dto) { // Insertar
        Pago p = modelM.map(dto, Pago.class);
        if (dto.getContrato() != null) {
            p.setContrato(contratoService.buscarContratoPorId(dto.getContrato().getId()));
        } else {
            throw new IllegalArgumentException("El contrato no puede ser nulo");
        }
        return modelM.map(servicio.guardarPago(p), PagoDTO.class); // Regresa el pago con el id generado
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PagoDTO> actualizarPago(@PathVariable int id, @RequestBody PagoDTO dto){ // Modificar
        Pago p = servicio.buscarPagoPorId(id);
        p.setDescripcion(dto.getDescripcion());
        p.setImporte(dto.getImporte());
        p.setTipoMoneda(dto.getTipoMoneda());
        p.setMedio(dto.getMedio());
        p.setFechaPago(dto.getFechaPago());
        p.setFechaVencimiento(dto.getFechaVencimiento());
        p.setContrato(dto.getContrato());
        servicio.guardarPago(p);
        return ResponseEntity.ok(modelM.map(p, PagoDTO.class));
    }

    @GetMapping("/pagoscercanos")
    @PreAuthorize("hasAnyAuthority('COMPRADOR', 'ARRENDATARIO', 'ADMIN')")
    public List<PagosCercanosDTO> obtenerPagosCercanos() {
        List<PagosCercanosDTO> dtoLista = new ArrayList<>();
            List<Object[]> filaLista=servicio.paymentsByDate();
        for(Object[] columna:filaLista) {
            PagosCercanosDTO dto = new PagosCercanosDTO();
            dto.setIdPago((Integer) columna[0]);
            dto.setFechaPago((Instant) columna[1]);
            dto.setInmueble((String) columna[2]);
            dto.setImporte((BigDecimal) columna[3]);
            dto.setTipoMoneda((String) columna[4]);
            dto.setComprador((String) columna[5]);
            dto.setVendedor((String) columna[6]);
            dtoLista.add(dto);
        }
        return dtoLista;
    }

    @GetMapping("/importesXmoneda")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ImportePorMonedaDTO> obtenerImportesXMoneda() {
        List<ImportePorMonedaDTO> dtoLista = new ArrayList<>();
        List<String[]> filaLista=servicio.importByTipeCoin();
        for(String[] columna:filaLista) {
            ImportePorMonedaDTO dto = new ImportePorMonedaDTO();
            dto.setTipoMoneda(columna[0]);
            dto.setTotalImporte(Double.parseDouble(columna[1]));
            dtoLista.add(dto);
        }
        return dtoLista;
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String,Boolean>> eliminarContrato(@PathVariable int id) {
        var pago = servicio.buscarPagoPorId(id);
        servicio.EliminarPago(pago);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }
}
