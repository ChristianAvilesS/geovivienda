package com.geovivienda.geovivienda.repositories;


import com.geovivienda.geovivienda.entities.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IAnuncioRepository extends JpaRepository<Anuncio, Integer> {
}
