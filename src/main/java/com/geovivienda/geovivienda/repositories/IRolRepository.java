package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer> {
    @Query("SELECT r FROM Rol r WHERE r.rol = :rol")
    Rol buscarRolPorNombre(@Param("rol") String nombre);

    @Query("SELECT r FROM Rol r WHERE r.rol != 'ADMIN'")
    List<Rol> listarRolesSinAdmin();

    @Modifying
    @Query(value = "UPDATE roles_x_usuario SET id_rol = :idNuevo WHERE id_rol = :idAntiguo", nativeQuery = true)
    void actualizarRolesUsuarioPorCambioAdmin(@Param("idNuevo") Integer idNuevo, @Param("idAntiguo") Integer idAntiguo);


}
