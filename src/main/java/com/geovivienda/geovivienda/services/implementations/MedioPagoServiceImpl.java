package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.MedioPago;
import com.geovivienda.geovivienda.repositories.IMedioPagoRepository;
import com.geovivienda.geovivienda.services.interfaces.IMedioPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedioPagoServiceImpl implements IMedioPagoService {

    @Autowired
    private IMedioPagoRepository repos;

    @Override
    public List<MedioPago> listarMedioPago() {
        return repos.findAll();
    }

    @Override
    public MedioPago guardarMedioPago(MedioPago medioPago) {
        return repos.save(medioPago);
    }

    @Override
    public MedioPago buscarMedioPagoPorId(int id) {
        return repos.findById(id).orElse(null);
    }

    @Override
    public void eliminarMedioPago(int id) {
        repos.deleteById(id);
    }

}
