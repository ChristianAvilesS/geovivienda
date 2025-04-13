package com.geovivienda.geovivienda.entities;

import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "inmuebles_x_usuario")
public class InmuebleUsuario {
    @EmbeddedId
    private InmuebleUsuarioId id;

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @MapsId("idInmueble")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_inmueble", nullable = false)
    private Inmueble idInmueble;

    @Column(name = "es_duenio")
    private Boolean esDuenio;

    @Column(name = "es_favorito")
    private Boolean esFavorito;

}