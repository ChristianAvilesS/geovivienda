package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.ImportePorMonedaDTO;
import com.geovivienda.geovivienda.dtos.PagoDTO;
import com.geovivienda.geovivienda.dtos.PagosCercanosDTO;
import com.geovivienda.geovivienda.entities.Pago;
import com.geovivienda.geovivienda.services.interfaces.IPagoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/pagos")
public class PagoController {
    private final ModelMapper modelM = new ModelMapper();
    @Autowired
    private IPagoService servicio;

    @GetMapping
    public List<PagoDTO> obtenerPagos() { // Listar
        return servicio.listarPagos().stream().map(p -> modelM.map(p, PagoDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public PagoDTO agregarPago(@RequestBody PagoDTO dto) { // Insertar
        Pago p = modelM.map(dto, Pago.class);
        return modelM.map(servicio.guardarPago(p), PagoDTO.class); // Regresa el pago con el id generado
    }

    @GetMapping("/pagoscercanos")
    public List<PagosCercanosDTO> obtenerPagosCercanos() {
        List<PagosCercanosDTO> dtoLista = new ArrayList<>();
            List<Object[]> filaLista=servicio.paymentsByDate();
        for(Object[] columna:filaLista) {
            PagosCercanosDTO dto = new PagosCercanosDTO();
            dto.setIdPago((Integer) columna[0]);
            dto.setFechaVencimiento((Instant) columna[1]);
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
}
