package com.geovivienda.geovivienda.dtos;

import com.geovivienda.geovivienda.entities.Direccion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InmuebleDTO {

    private Integer id;

    private String nombre;

    private String tipo;

    private Direccion direccion;

    private BigDecimal area;

    private BigDecimal precioBase;

    private String descripcion;

    private String estado;
}
