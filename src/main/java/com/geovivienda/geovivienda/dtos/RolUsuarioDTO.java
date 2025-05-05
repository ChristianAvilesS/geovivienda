package com.geovivienda.geovivienda.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geovivienda.geovivienda.entities.Rol;
import com.geovivienda.geovivienda.entities.Usuario;
import com.geovivienda.geovivienda.entities.ids.RolUsuarioId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolUsuarioDTO {
    private RolUsuarioId idRolUsuario;
    private Usuario usuario;
    @JsonIgnore
    private Rol rol;
}
