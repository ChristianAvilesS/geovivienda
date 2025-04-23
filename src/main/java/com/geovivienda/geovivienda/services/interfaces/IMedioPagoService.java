package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.MedioPago;

import java.util.List;

public interface IMedioPagoService {
    List<MedioPago> listarMedioPago();
    // Cumple función de insert y update
    MedioPago guardarMedioPago(MedioPago medioPago);
    MedioPago buscarMedioPagoPorId(int id);
    void eliminarMedioPago(int id);
}
