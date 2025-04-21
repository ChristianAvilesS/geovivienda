package com.geovivienda.geovivienda.dtos;

import com.geovivienda.geovivienda.entities.Contrato;
import com.geovivienda.geovivienda.entities.MedioPago;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
public class PagoDTO {
    private Integer id;
    private String descripcion;
    private BigDecimal importe;
    private String tipoMoneda;
    private MedioPago idMedio;
    private Instant fechaPago;
    private Instant fechaVencimiento;
    private Contrato idContrato;
}
