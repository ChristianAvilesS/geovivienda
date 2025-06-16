package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Pago;
import com.geovivienda.geovivienda.repositories.IPagoRepository;
import com.geovivienda.geovivienda.services.interfaces.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoServiceImpl implements IPagoService {
    @Autowired
    private IPagoRepository repos;

    @Override
    public List<Pago> listarPagos() {
        return repos.findAll();
    }

    @Override
    public Pago guardarPago(Pago pago){
        return repos.save(pago);
    }

    @Override
    public void EliminarPago(Pago pago) { repos.delete(pago);}

    @Override
    public Pago buscarPagoPorId(Integer id) {return repos.findById(id).orElse(null);
    }

    @Override
    public List<String[]> importByTipeCoin() {return repos.importByTipeCoin();}

    @Override
    public List<Object[]> paymentsByDate() {return repos.paymentsByDate();}
}
