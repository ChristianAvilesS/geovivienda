package com.geovivienda.geovivienda.dtos;

import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.entities.Usuario;
import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InmuebleUsuarioDTO {
    private InmuebleUsuarioId id;

    private Usuario idUsuario;

    private Inmueble idInmueble;

    private Boolean esDuenio;

    private Boolean esFavorito;
}
