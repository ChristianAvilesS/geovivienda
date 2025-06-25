package com.geovivienda.geovivienda.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
public class PagosCercanosDTO {
    private Integer idPago;
    private Instant fechaPago;
    private String inmueble;
    private BigDecimal importe;
    private String tipoMoneda;
    private String comprador;
    private String vendedor;

}
