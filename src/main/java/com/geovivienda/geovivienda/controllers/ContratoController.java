package com.geovivienda.geovivienda.controllers;


import com.geovivienda.geovivienda.dtos.ContratoDTO;
import com.geovivienda.geovivienda.entities.Contrato;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IContratoService;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleService;
import com.geovivienda.geovivienda.services.interfaces.IUsuarioService;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IContratoService servicio;

    @Autowired
    private IUsuarioService userService;

    @Autowired
    private IInmuebleService inmuebleService;

    @GetMapping
    public List<ContratoDTO> obtenerContratos() {
        return servicio.listarContratos().stream()
                .map(contrato -> modelM.map(contrato, ContratoDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public ContratoDTO agregarContrato(@RequestBody ContratoDTO dto){
        var contrato = modelM.map(dto, Contrato.class);
        contrato.setComprador(userService.buscarUsuarioPorId(dto.getComprador().getIdUsuario()));
        contrato.setInmueble(inmuebleService.buscarInmueblePorId(dto.getInmueble().getIdInmueble()));
        contrato.setVendedor(userService.buscarUsuarioPorId(dto.getVendedor().getIdUsuario()));
        return modelM.map(servicio.guardarContrato(contrato), ContratoDTO.class);
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
