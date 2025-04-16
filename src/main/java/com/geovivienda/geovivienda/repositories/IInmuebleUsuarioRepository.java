package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.InmuebleUsuario;
import com.geovivienda.geovivienda.entities.ids.InmuebleUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInmuebleUsuarioRepository extends JpaRepository<InmuebleUsuario, InmuebleUsuarioId> {
}
