package com.geovivienda.geovivienda.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(length = 200)
    private String nombre;

    @Column(length = 20)
    private String telefono;

    @OneToOne
    @JoinColumn(name = "id_direccion", referencedColumnName = "id_direccion")
    private Direccion direccion;

    @Column(length = 20)
    private String username;

    @Column(length = 20)
    private String email;

    @Column(length = 128)
    private String password;

    private boolean inactivo;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(Integer idUsuario, String nombre, String telefono, Direccion direccion, String username,
                   String email, String password, boolean inactivo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.username = username;
        this.email = email;
        this.password = password;
        this.inactivo = inactivo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isInactivo() {
        return inactivo;
    }

    public void setInactivo(boolean inactivo) {
        this.inactivo = inactivo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion=" + direccion +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", inactivo=" + inactivo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(idUsuario, usuario.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idUsuario);
    }
}
