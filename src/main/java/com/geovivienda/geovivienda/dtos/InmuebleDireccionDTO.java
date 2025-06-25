package com.geovivienda.geovivienda.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InmuebleDireccionDTO {
    private String nombre;
    private String direccion;
    private String tipo;
    private BigDecimal area;
    private BigDecimal precioBase;
    private String descripcion;
}
