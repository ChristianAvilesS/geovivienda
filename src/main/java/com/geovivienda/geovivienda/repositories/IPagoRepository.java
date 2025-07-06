package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPagoRepository extends JpaRepository<Pago, Integer> {

    @Query(value = """
            SELECT
                tipo_moneda,
                SUM(importe) AS total_importe
             FROM pagos
             GROUP BY tipo_moneda
            """, nativeQuery = true)
    List<String[]> importByTipeCoin();

    @Query(value = """
            SELECT
                p.id_pago,
                p.fecha_pago,
                i.nombre AS inmueble,
                p.importe,
                p.tipo_moneda,
                uc.nombre AS comprador,
                uv.nombre AS vendedor
             FROM pagos p
             JOIN contratos c ON p.id_contrato = c.id_contrato
             JOIN inmuebles i ON c.id_inmueble = i.id_inmueble
             JOIN usuarios uc ON c.id_comprador = uc.id_usuario
             JOIN usuarios uv ON c.id_vendedor = uv.id_usuario
             WHERE p.fecha_pago >= CURRENT_DATE
             ORDER BY p.fecha_pago ASC
            """, nativeQuery = true)
    List<Object[]> paymentsByDate();

    @Query("SELECT p.medio.medioPago, SUM(p.importe) FROM Pago p " +
            "WHERE p.fechaPago IS NOT NULL " +
            "GROUP BY p.medio.medioPago, p.medio.idMedio " +
            "ORDER BY p.medio.idMedio")
    List<String[]> reporteDineroRecaudadoPorTipoPago();
}
