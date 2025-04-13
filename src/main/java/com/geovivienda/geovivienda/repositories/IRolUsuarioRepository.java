package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.RolUsuario;
import com.geovivienda.geovivienda.entities.ids.RolUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolUsuarioRepository extends JpaRepository<RolUsuario, RolUsuarioId> {
}
