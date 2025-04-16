package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Contrato;
import com.geovivienda.geovivienda.repositories.IContratoRepository;
import com.geovivienda.geovivienda.services.interfaces.IContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratoServiceImpl implements IContratoService {

    @Autowired
    private IContratoRepository repos;

    @Override
    public List<Contrato> listarContratos() {
        return repos.findAll();
    }

    @Override
    public Contrato buscarContratoPorId(Integer id) {
        return repos.findById(id).orElse(null);
    }

    @Override
    public Contrato guardarContrato(Contrato contrato) {
        return repos.save(contrato);
    }

    @Override
    public void eliminarContrato(Contrato contrato) {
        repos.delete(contrato);
    }
}
