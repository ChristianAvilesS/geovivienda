package com.geovivienda.geovivienda.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportePorcentajeTiposUsuarioDTO {
    private String tipoUsuario;
    private Double porcentaje;
}
