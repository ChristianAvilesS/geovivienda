package com.geovivienda.geovivienda.dtos;

import com.geovivienda.geovivienda.entities.PK_RolUsuario;
import com.geovivienda.geovivienda.entities.Rol;
import com.geovivienda.geovivienda.entities.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolUsuarioDTO {
    private PK_RolUsuario idRolUsuario;
    private Usuario usuario;
    private Rol rol;
}
