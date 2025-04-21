package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.MedioPago;

import java.util.List;

public interface IMedioPagoService {
    public List<MedioPago> listarMedioPago();
    public MedioPago guardarMedioPago(MedioPago medioPago);
    public void actualizarMedioPago(MedioPago medioPago);
    public void eliminarMedioPago(int id);
}
