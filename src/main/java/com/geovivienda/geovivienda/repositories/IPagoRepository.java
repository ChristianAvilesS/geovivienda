package com.geovivienda.geovivienda.repositories;

import com.geovivienda.geovivienda.entities.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPagoRepository extends JpaRepository<Pago, Integer> {

    @Query(value="SELECT \n" +
            "    tipo_moneda,\n" +
            "    SUM(importe) AS total_importe\n" +
            " FROM pagos\n" +
            " GROUP BY tipo_moneda",nativeQuery = true)
    public List<String[]> importByTipeCoin();

    @Query(value="SELECT \n" +
            "    p.id_pago,\n" +
            "    p.fecha_vencimiento,\n" +
            "    i.nombre AS inmueble,\n" +
            "    p.importe,\n" +
            "    p.tipo_moneda,\n" +
            "    uc.nombre AS comprador,\n" +
            "    uv.nombre AS vendedor\n" +
            " FROM pagos p\n" +
            " JOIN contratos c ON p.id_contrato = c.id_contrato\n" +
            " JOIN inmuebles i ON c.id_inmueble = i.id_inmueble\n" +
            " JOIN usuarios uc ON c.id_comprador = uc.id_usuario\n" +
            " JOIN usuarios uv ON c.id_vendedor = uv.id_usuario\n" +
            " WHERE p.fecha_vencimiento >= CURRENT_DATE\n" +
            " ORDER BY p.fecha_vencimiento ASC",nativeQuery = true)
    public List<Object[]> paymentsByDate();
}
