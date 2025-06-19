package com.geovivienda.geovivienda.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AprobarCompraInmuebleDTO {
    int idInmueble;
    int idUsuarioComprador;
    int idUsuarioVendedor;
    String descripcionContrato;
    String tipoContrato;
    LocalDate fechaVencimientoContrato;
}
