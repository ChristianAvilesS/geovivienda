package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.PK_RolUsuario;
import com.geovivienda.geovivienda.entities.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolUsuarioRepository extends JpaRepository<RolUsuario, PK_RolUsuario> {
}
