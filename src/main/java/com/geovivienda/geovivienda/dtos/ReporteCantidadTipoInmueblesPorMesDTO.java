package com.geovivienda.geovivienda.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
public class ReporteCantidadTipoInmueblesPorMesDTO {
    private String mes;
    private Integer cantidadCompra;
    private Integer cantidadAlquiler;

    public ReporteCantidadTipoInmueblesPorMesDTO(int mes, Integer cantidadCompra, Integer cantidadAlquiler) {
        this.mes = Month.of(mes).getDisplayName(TextStyle.SHORT, new Locale("es"));
        this.cantidadCompra = cantidadCompra;
        this.cantidadAlquiler = cantidadAlquiler;
    }
}
