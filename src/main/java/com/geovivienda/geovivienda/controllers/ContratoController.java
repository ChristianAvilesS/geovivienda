package com.geovivienda.geovivienda.controllers;


import com.geovivienda.geovivienda.dtos.ContratoDTO;
import com.geovivienda.geovivienda.entities.Contrato;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IContratoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/contratos")
public class ContratoController {
    private static final Logger logger = LoggerFactory.getLogger(DireccionController.class);
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IContratoService servicio;
    @GetMapping("/")
    public List<ContratoDTO> obtenerContratos() {
        var contratos = servicio.listarContratos();
        contratos.forEach(contrato -> logger.info(contrato.toString()));
        return contratos.stream().map(contrato -> modelM.map(contrato, ContratoDTO.class)).collect(Collectors.toList());
    }

    @PostMapping("/")
    public ContratoDTO agregarContrato(@RequestBody Contrato contrato){
        logger.info("Contrato a ingresar: "+ contrato);
        var contratoGuardado = this.servicio.guardarContrato(contrato);
        return modelM.map(contratoGuardado, ContratoDTO.class);
    @GetMapping
    public List<ContratoDTO> obtenerContratos() {
        return servicio.listarContratos().stream()
                .map(contrato -> modelM.map(contrato, ContratoDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public ContratoDTO agregarContrato(@RequestBody ContratoDTO dto){
        return modelM.map(servicio.guardarContrato(modelM.map(dto, Contrato.class)), ContratoDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> obtenerContratoPorId(@PathVariable int id) {
        Contrato contrato = servicio.buscarContratoPorId(id);
        if(contrato != null){
            return ResponseEntity.ok(modelM.map(contrato, ContratoDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontro el contrato con el id: "+id);
    }
      
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Boolean>> eliminarContrato(@PathVariable int id) {
        var contrato = servicio.buscarContratoPorId(id);
        servicio.eliminarContrato(contrato);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }
}
