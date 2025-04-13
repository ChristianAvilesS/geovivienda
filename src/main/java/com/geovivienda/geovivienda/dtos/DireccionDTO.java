package com.geovivienda.geovivienda.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DireccionDTO {
    private Integer idDireccion;
    private String direccion;
    private double latitud;
    private double longitud;
}
