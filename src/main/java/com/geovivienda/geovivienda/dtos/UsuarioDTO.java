package com.geovivienda.geovivienda.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String password;
    @JsonIgnore
    private boolean inactivo;
}
