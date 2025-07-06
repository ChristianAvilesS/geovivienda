package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE u.username = :username")
    Usuario findUsuarioByUsername(@Param("username") String username);

    @Query("SELECT u FROM Usuario u WHERE u.inactivo = false")
    List<Usuario> listNotEliminated();

    @Query("""
            SELECT CASE WHEN u.inactivo = true THEN 'Eliminado' ELSE 'Activo' END, COUNT(u.idUsuario)
            FROM Usuario u GROUP BY u.inactivo
            """)
    List<String[]> reporteEstadoUsuarios();

    @Query(value = """
            SELECT r.rol,
            ROUND(COUNT(ru.id_usuario) * 100.0 / CAST((SELECT COUNT(rux.id_usuario)
                 FROM public.roles_x_usuario rux) AS numeric),2) as Porcentaje
            FROM public.roles_x_usuario ru
            JOIN public.roles r ON r.id_rol = ru.id_rol
            GROUP BY r.rol, r.id_rol
            ORDER BY r.id_rol
            """, nativeQuery = true)
    List<String[]> reportePorcentajeDeUsuariosPorTipo();
}
