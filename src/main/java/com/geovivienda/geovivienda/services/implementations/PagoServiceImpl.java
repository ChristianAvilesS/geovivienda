package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Contrato;
import com.geovivienda.geovivienda.entities.MedioPago;
import com.geovivienda.geovivienda.entities.Pago;
import com.geovivienda.geovivienda.repositories.IContratoRepository;
import com.geovivienda.geovivienda.repositories.IMedioPagoRepository;
import com.geovivienda.geovivienda.repositories.IPagoRepository;
import com.geovivienda.geovivienda.services.interfaces.IPagoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PagoServiceImpl implements IPagoService {
    @Autowired
    private IPagoRepository repos;
    @Autowired
    private IMedioPagoRepository medioRepo;
    @Autowired
    private IContratoRepository contratoRepo;

    @Override
    public List<Pago> listarPagos() {
        return repos.findAll();
    }

    @Override
    public Pago guardarPago(Pago pago){
        if (pago.getMedio() != null && pago.getMedio().getIdMedio() != null) {
            MedioPago medioExistente = medioRepo.findById(pago.getMedio().getIdMedio())
                    .orElseThrow(() -> new RuntimeException("Medio de pago no válido"));

            // Asignar el medio recuperado
            pago.setMedio(medioExistente);
        } else {
            throw new RuntimeException("El medio de pago es obligatorio");
        }
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
