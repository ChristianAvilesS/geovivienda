package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
}
