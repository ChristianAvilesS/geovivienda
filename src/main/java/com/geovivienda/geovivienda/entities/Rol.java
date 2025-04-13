package com.geovivienda.geovivienda.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", unique = true, nullable = false)
    private Integer idRol;

    @Column(name = "rol", length = 20)
    private String rol;

    public Rol() {
    }

    public Rol(Integer idRol) {
        this.idRol = idRol;
    }

    public Rol(Integer idRol, String rol) {
        this.idRol = idRol;
        this.rol = rol;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "idRol=" + idRol +
                ", rol='" + rol + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rol rol = (Rol) o;
        return Objects.equals(idRol, rol.idRol);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idRol);
    }
}
