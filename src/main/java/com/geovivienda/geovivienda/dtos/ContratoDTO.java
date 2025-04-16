package com.geovivienda.geovivienda.dtos;

import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.entities.Usuario;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ContratoDTO {
    private Integer id;

    private Usuario idVendedor;

    private Inmueble idInmueble;

    private Usuario idComprador;

    private String tipoContrato;

    private String descripcion;

    private BigDecimal montototal;

    private LocalDateTime fechaFirma;

    private LocalDateTime fechaVencimiento;
}
