package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer> {
}
