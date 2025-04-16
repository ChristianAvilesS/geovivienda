package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContratoRepository extends JpaRepository<Contrato, Integer> {}
