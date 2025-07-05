package com.geovivienda.geovivienda.dtos;

import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.entities.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ValoracionDTO {
    private Integer idValoracion;
    private Usuario usuario;
    private BigDecimal rating;
    private Inmueble inmueble;

}
