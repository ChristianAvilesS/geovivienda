package com.geovivienda.geovivienda.dtos;

import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.entities.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class ContratoDTO {
    private Integer id;
  
    private Usuario vendedor;

    private Inmueble inmueble;

    private Usuario comprador;

    private String tipoContrato;

    private String descripcion;

    private BigDecimal montoTotal;

    private Instant fechaFirma;

    private Instant fechaVencimiento;
}
