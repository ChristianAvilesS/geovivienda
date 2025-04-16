package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInmuebleRepository extends JpaRepository<Inmueble, Integer> {}
