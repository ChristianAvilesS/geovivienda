package com.geovivienda.geovivienda.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "imagenes")
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_inmueble", nullable = false)
    private Inmueble idInmueble;

    @Column(name = "url", length = 200)
    private String url;

    @Column(name = "titulo", length = 50)
    private String titulo;

}