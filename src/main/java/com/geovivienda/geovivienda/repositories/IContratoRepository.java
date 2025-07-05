package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IContratoRepository extends JpaRepository<Contrato, Integer> {
    @Query(value = """
                SELECT meses.mes,
                COUNT(c.tipo_contrato) FILTER (WHERE c.tipo_contrato = 'COMPRA') AS compra,
                COUNT(c.tipo_contrato) FILTER (WHERE c.tipo_contrato = 'ARRENDAMIENTO') AS alquiler
                FROM public.contratos c RIGHT JOIN (SELECT generate_series(1, 12) AS mes) meses
                ON meses.mes = EXTRACT(MONTH FROM c.fecha_firma) AND TO_CHAR(c.fecha_firma, 'YYYY') = :anio
                AND c.fecha_firma IS NOT NULL
                GROUP BY EXTRACT(MONTH FROM c.fecha_firma), meses.mes
                ORDER BY meses.mes;
            """, nativeQuery = true)
    List<String[]> reporteCantidadInmueblesPorTipoContratoPorMesEnAnio(@Param("anio") String anio);
}
