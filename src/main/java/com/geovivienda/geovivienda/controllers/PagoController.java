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
    @Autowired
    private IPagoService pS;

    @GetMapping("/lista")
    public List<PagoDTO> listar() {
        return pS.list().stream().map(p -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(p, PagoDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping("/prueba")
    public void insertar(@RequestBody PagoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Pago p = modelMapper.map(dto, Pago.class);
        pS.insert(p);
    }
}
