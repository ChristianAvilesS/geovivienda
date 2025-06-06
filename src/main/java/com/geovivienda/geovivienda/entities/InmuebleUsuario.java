package com.geovivienda.geovivienda.entities;

import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inmuebles_x_usuario")
public class InmuebleUsuario {
    @EmbeddedId
    private InmuebleUsuarioId id;

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @MapsId("idInmueble")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "id_inmueble", nullable = false)
    private Inmueble inmueble;

    @Column(name = "es_duenio")
    private Boolean esDuenio;

    @Column(name = "es_favorito")
    private Boolean esFavorito;

    @Column(name = "estado_solicitud")
    private String estadoSolicitud; //Estados: ninguno, solicitado, aprobado, rechazado, comprado

    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud;

}