package com.geovivienda.geovivienda.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class DireccionDTO {
    private Integer idDireccion;
    private String direccion;
    private BigDecimal latitud;
    private BigDecimal longitud;

    public DireccionDTO(String direccion, BigDecimal latitud, BigDecimal longitud) {
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "DireccionDTO{" +
                "idDireccion=" + idDireccion +
                ", direccion='" + direccion + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}
