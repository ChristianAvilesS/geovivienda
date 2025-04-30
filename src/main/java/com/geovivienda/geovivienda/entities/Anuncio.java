package com.geovivienda.geovivienda.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "anuncios")
public class Anuncio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anuncio", nullable = false)
    private Integer idAnuncio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "id_anunciante", nullable = false)
    private Usuario anunciante;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "id_inmueble", nullable = false)
    private Inmueble inmueble;

    public Anuncio()
    {}

    public Anuncio(Usuario anunciante, String descripcion, LocalDateTime fechaPublicacion, Inmueble inmueble) {
        this.anunciante = anunciante;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.inmueble = inmueble;
    }
}