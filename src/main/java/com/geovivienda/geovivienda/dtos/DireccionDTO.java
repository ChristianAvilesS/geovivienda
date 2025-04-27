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

    public DireccionDTO() {
    }

    public DireccionDTO(String direccion, BigDecimal latitud, BigDecimal longitud) {
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
