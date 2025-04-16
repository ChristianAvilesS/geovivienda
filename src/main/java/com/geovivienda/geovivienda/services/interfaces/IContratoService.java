package com.geovivienda.geovivienda.services.interfaces;

import com.geovivienda.geovivienda.entities.Contrato;

import java.util.List;

public interface IContratoService {

    public List<Contrato> listarContratos();
    public Contrato buscarContratoPorId(Integer id);

    public Contrato guardarContrato(Contrato contrato);
    public void eliminarContrato(Contrato contrato);
}
