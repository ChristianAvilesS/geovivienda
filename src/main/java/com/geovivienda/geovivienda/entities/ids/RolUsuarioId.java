package com.geovivienda.geovivienda.entities.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class RolUsuarioId implements Serializable {
    @Serial
    private static final long serialVersionUID = -7926026804959748869L;
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "id_rol", nullable = false)
    private Integer idRol;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RolUsuarioId entity = (RolUsuarioId) o;
        return Objects.equals(this.idRol, entity.idRol) &&
                Objects.equals(this.idUsuario, entity.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRol, idUsuario);
    }

}