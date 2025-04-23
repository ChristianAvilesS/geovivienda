package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.PagoDTO;
import com.geovivienda.geovivienda.entities.Pago;
import com.geovivienda.geovivienda.services.interfaces.IPagoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
