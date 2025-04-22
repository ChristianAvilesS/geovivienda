package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImagenRepository extends JpaRepository<Imagen, Integer> {
}


