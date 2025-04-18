package com.geovivienda.geovivienda.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DireccionDTO {
    private Integer idDireccion;
    private String direccion;
    private BigDecimal latitud;
    private BigDecimal longitud;
}
