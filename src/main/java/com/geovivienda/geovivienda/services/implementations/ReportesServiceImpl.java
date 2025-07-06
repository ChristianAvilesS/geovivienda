package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.repositories.IContratoRepository;
import com.geovivienda.geovivienda.repositories.IPagoRepository;
import com.geovivienda.geovivienda.repositories.IUsuarioRepository;
import com.geovivienda.geovivienda.services.interfaces.IReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportesServiceImpl implements IReportesService {
    @Autowired
    private IUsuarioRepository userRepos;

    @Autowired
    private IContratoRepository contratoRepos;

    @Autowired
    private IPagoRepository pagoRepos;

    @Override
    public List<String[]> reporteEstadoUsuarios() {
        return userRepos.reporteEstadoUsuarios();
    }

    @Override
    public List<String[]> reportePorcentajeDeUsuariosPorTipo() {
        return userRepos.reportePorcentajeDeUsuariosPorTipo();
    }

    @Override
    public List<String[]> reporteCantidadInmueblesPorTipoContratoPorMesEnAnio(String anio) {
        return contratoRepos.reporteCantidadInmueblesPorTipoContratoPorMesEnAnio(anio);
    }

    @Override
    public List<String[]> reporteDineroRecaudadoPorTipoPago() {
        return pagoRepos.reporteDineroRecaudadoPorTipoPago();
    }
}
