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
    public List<Pago> list() {return repos.findAll();}

    @Override
    public void insert(Pago pago){ repos.save(pago);}
}
