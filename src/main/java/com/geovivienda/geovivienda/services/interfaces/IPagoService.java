package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Pago;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IPagoService {
    List<Pago> listarPagos();
    Pago guardarPago(Pago pago);
    void EliminarPago(Pago pago);
    Pago buscarPagoPorId(Integer id);
    List<String[]> importByTipeCoin();
    List<Object[]> paymentsByDate();
}
