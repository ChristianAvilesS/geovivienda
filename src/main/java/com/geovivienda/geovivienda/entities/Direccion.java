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
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Integer idDireccion;

    @Column(length = 100)
    private String direccion;

    private double latitud;

    private double longitud;

    public Direccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }
}
