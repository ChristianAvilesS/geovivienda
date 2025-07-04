package com.geovivienda.geovivienda.dtos;

import com.geovivienda.geovivienda.securities.JwtRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CambioPasswordDTO {
    private JwtRequest jwt;
    private String nuevoPassword;
}
