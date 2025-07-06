package com.geovivienda.geovivienda.services.interfaces;

import java.util.List;

public interface IReportesService {
    List<String[]> reporteEstadoUsuarios();
    List<String[]> reportePorcentajeDeUsuariosPorTipo();
    List<String[]> reporteCantidadInmueblesPorTipoContratoPorMesEnAnio(String anio);
    List<String[]> reporteDineroRecaudadoPorTipoPago();
}
