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
@Table(name = "direcciones")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion", nullable = false)
    private Integer idDireccion;

    @Column(name = "direccion", length = 100)
    private String direccion;

    @Column(name = "longitud", precision = 11, scale = 7)
    private BigDecimal longitud;

    @Column(name = "latitud", precision = 11, scale = 7)
    private BigDecimal latitud;

}