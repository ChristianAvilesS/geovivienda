package com.geovivienda.geovivienda.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FinalizarCompraInmuebleDTO {
    int idContrato;
    int idMedioPago;
    String descripcionPago;
    String tipoMoneda;
    BigDecimal importe;
    LocalDate fechaVencimientoPago;
}
