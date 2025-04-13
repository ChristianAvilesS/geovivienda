package com.geovivienda.geovivienda.entities;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PK_RolUsuario {

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "id_rol")
    private Integer idRol;

}
