package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.MedioPagoDTO;
import com.geovivienda.geovivienda.dtos.PagoDTO;
import com.geovivienda.geovivienda.entities.MedioPago;
import com.geovivienda.geovivienda.entities.Pago;
import com.geovivienda.geovivienda.services.interfaces.IMedioPagoService;
import com.geovivienda.geovivienda.services.interfaces.IPagoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/mediopago")
public class MedioPagoController {

    @Autowired
    private IMedioPagoService mS;

    @GetMapping
    public List<MedioPagoDTO> listar(){

        return mS.listarMedioPago().stream().map(p -> {
            ModelMapper m = new ModelMapper();
            return m.map(p, MedioPagoDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void insertar(@RequestBody MedioPagoDTO mp){
        ModelMapper m = new ModelMapper();
        MedioPago mp1 = m.map(mp, MedioPago.class);
        mS.guardarMedioPago(mp1);
    }

    @PutMapping
    public void modificar(@RequestBody MedioPagoDTO dto){
        ModelMapper m = new ModelMapper();
        MedioPago mp = m.map(dto, MedioPago.class);
        mS.actualizarMedioPago(mp);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") int id){mS.eliminarMedioPago(id);}
}
