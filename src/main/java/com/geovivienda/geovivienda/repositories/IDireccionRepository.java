package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDireccionRepository extends JpaRepository<Direccion, Integer> {
}
