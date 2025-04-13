package com.geovivienda.geovivienda.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "direcciones")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RolUsuario {

    @EmbeddedId
    private PK_RolUsuario idRolUsuario;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @MapsId("idUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    @MapsId("idRol")
    private Rol rol;



}
