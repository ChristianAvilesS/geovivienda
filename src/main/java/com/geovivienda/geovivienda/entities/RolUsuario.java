package com.geovivienda.geovivienda.entities;

import com.geovivienda.geovivienda.entities.ids.RolUsuarioId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles_x_usuario")
public class RolUsuario {
    @EmbeddedId
    private RolUsuarioId id;

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @MapsId("idRol")
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

}