package com.geovivienda.geovivienda.dtos;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ValoracionInmuebleDTO {
    private String nombreInmueble;
    private BigDecimal valoracion;

}
