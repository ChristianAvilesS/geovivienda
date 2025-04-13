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
public class InmuebleUsuarioId implements Serializable {
    @Serial
    private static final long serialVersionUID = -5520092332232384504L;
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "id_inmueble", nullable = false)
    private Integer idInmueble;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InmuebleUsuarioId entity = (InmuebleUsuarioId) o;
        return Objects.equals(this.idInmueble, entity.idInmueble) &&
                Objects.equals(this.idUsuario, entity.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInmueble, idUsuario);
    }

}