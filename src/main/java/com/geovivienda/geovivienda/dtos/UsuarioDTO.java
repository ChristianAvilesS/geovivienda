package com.geovivienda.geovivienda.dtos;

import com.geovivienda.geovivienda.entities.Direccion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Integer idUsuario;
    private String nombre;
    private String telefono;
    private Direccion direccion;
    private String username;
    private String email;
    private String password;
    private boolean inactivo;
}
