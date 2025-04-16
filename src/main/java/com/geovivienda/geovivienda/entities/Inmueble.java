package com.geovivienda.geovivienda.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "inmuebles")
public class Inmueble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inmueble", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "tipo", length = 100)
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_direccion", nullable = false)
    private Direccion idDireccion;

    @Column(name = "area", precision = 10, scale = 2)
    private BigDecimal area;

    @Column(name = "precio_base", precision = 11, scale = 3)
    private BigDecimal precioBase;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "estado", length = 20)
    private String estado;

}