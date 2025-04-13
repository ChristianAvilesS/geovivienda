package com.geovivienda.geovivienda.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 200)
    private String nombre;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_direccion", nullable = false)
    private Direccion idDireccion;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "email", length = 20)
    private String email;

    @Column(name = "password", length = 128)
    private String password;

    @Column(name = "inactivo", nullable = false)
    private Boolean inactivo = false;

}