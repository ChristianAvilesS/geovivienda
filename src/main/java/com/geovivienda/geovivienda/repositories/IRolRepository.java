package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer> {
    @Query("SELECT r FROM Rol r WHERE r.rol = :rol")
    Rol buscarRolPorNombre(@Param("rol") String nombre);
}
