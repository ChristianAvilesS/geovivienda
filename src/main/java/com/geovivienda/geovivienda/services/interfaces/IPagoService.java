package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Pago;

import java.util.List;

public interface IPagoService {
    public List<Pago> list();
    public void insert(Pago pago);
}
