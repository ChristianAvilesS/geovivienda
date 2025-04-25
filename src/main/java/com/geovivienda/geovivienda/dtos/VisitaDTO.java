package com.geovivienda.geovivienda.dtos;

import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.entities.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class VisitaDTO {
    private Integer idVisita;
    private Inmueble inmueble;
    private Usuario usuario;
    private Instant fechaVisita;
}
