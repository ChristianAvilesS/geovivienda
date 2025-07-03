package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.ReporteCantidadRecaudacionMedioDTO;
import com.geovivienda.geovivienda.dtos.ReporteCantidadTipoInmueblesPorMesDTO;
import com.geovivienda.geovivienda.dtos.ReportePorcentajeTiposUsuarioDTO;
import com.geovivienda.geovivienda.dtos.ReporteUsuariosEliminadosDTO;
import com.geovivienda.geovivienda.services.interfaces.IReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/reportes")
public class ReportesController {
    @Autowired
    private IReportesService servicio;

    @GetMapping("reporte-estado-usuarios")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ReporteUsuariosEliminadosDTO> reporteEstadoUsuarios() {
        return servicio.reporteEstadoUsuarios().stream().map(fila ->
                new ReporteUsuariosEliminadosDTO(fila[0], Integer.parseInt(fila[1]))
        ).collect(Collectors.toList());
    }

    @GetMapping("reporte-porcentaje-tipos")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ReportePorcentajeTiposUsuarioDTO> reportePorcentajeDeUsuariosPorTipo() {
        return servicio.reportePorcentajeDeUsuariosPorTipo().stream().map(fila ->
                new ReportePorcentajeTiposUsuarioDTO(fila[0], Double.parseDouble(fila[1]))
        ).collect(Collectors.toList());
    }

    @GetMapping("reporte-contratos")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ReporteCantidadTipoInmueblesPorMesDTO> reporteCantidadInmueblesPorTipoContratoPorMesEnAnio(
            @RequestParam("anio") String anio) {
        return servicio.reporteCantidadInmueblesPorTipoContratoPorMesEnAnio(anio).stream().map(fila ->
                new ReporteCantidadTipoInmueblesPorMesDTO(Integer.parseInt(fila[0]),
                        Integer.parseInt(fila[1]), Integer.parseInt(fila[2]))).collect(Collectors.toList());
    }

    @GetMapping("reporte-recaudacion-medios")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ReporteCantidadRecaudacionMedioDTO> reporteDineroRecaudadoPorTipoPago() {
        return servicio.reporteDineroRecaudadoPorTipoPago().stream().map(fila ->
                new ReporteCantidadRecaudacionMedioDTO(fila[0], Double.parseDouble(fila[1]))
        ).collect(Collectors.toList());
    }


}
