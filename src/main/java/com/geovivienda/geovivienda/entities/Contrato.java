package com.geovivienda.geovivienda.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contratos")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vendedor", nullable = false)
    private Usuario idVendedor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_inmueble", nullable = false)
    private Inmueble idInmueble;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_comprador", nullable = false)
    private Usuario idComprador;

    @Column(name = "tipo_contrato", length = 200)
    private String tipoContrato;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "montototal", precision = 11, scale = 3)
    private BigDecimal montototal;

    @Column(name = "fecha_firma")
    private LocalDateTime fechaFirma;

    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;

}