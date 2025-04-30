package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.MedioPagoDTO;
import com.geovivienda.geovivienda.entities.MedioPago;
import com.geovivienda.geovivienda.services.interfaces.IMedioPagoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/mediospago")
public class MedioPagoController {
    private final ModelMapper modelM = new ModelMapper();
    
    @Autowired
    private IMedioPagoService servicio;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<MedioPagoDTO> obtenerMediosPago(){ // Listar
        return servicio.listarMedioPago().stream().map(p -> modelM.map(p, MedioPagoDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public MedioPagoDTO agregarMedioPago(@RequestBody MedioPagoDTO dto){ // Agregar
        MedioPago medio = modelM.map(dto, MedioPago.class);
        return modelM.map(servicio.guardarMedioPago(medio), MedioPagoDTO.class); // Devuelve el medio pago con el id generado
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MedioPagoDTO> actualizarMedioPago(@PathVariable int id, @RequestBody MedioPagoDTO dto){ // Modificar
        MedioPago mp = servicio.buscarMedioPagoPorId(id);
        mp.setMedioPago(dto.getMedioPago());
        servicio.guardarMedioPago(mp);
        return ResponseEntity.ok(modelM.map(mp, MedioPagoDTO.class));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> eliminarMedioPago(@PathVariable("id") int id){
        servicio.eliminarMedioPago(id);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }
}
