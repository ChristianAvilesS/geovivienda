package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Pago;

import java.util.List;

public interface IPagoService {
    List<Pago> listarPagos();
    Pago guardarPago(Pago pago);
    List<String[]> importByTipeCoin();
    List<Object[]> paymentsByDate();
}
