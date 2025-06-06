package com.geovivienda.geovivienda.dtos;

import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.entities.Usuario;
import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InmuebleUsuarioDTO {
    private InmuebleUsuarioId id;

    private Usuario usuario;

    private Inmueble inmueble;

    private Boolean esDuenio;

    private Boolean esFavorito;

    private String estadoSolicitud;

    private LocalDate fechaSolicitud;
}
