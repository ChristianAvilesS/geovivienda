package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IDireccionRepository extends JpaRepository<Direccion, Integer> {
    @Query("SELECT d FROM Direccion d WHERE d.direccion = :dir")
    List<Direccion> buscarDireccionPorDireccion(@Param("dir") String dir);
}
