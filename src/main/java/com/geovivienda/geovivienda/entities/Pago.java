package com.geovivienda.geovivienda.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago", nullable = false)
    private Integer idPago;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "importe", precision = 11, scale = 3)
    private BigDecimal importe;

    @Column(name = "tipo_moneda", length = 20)
    private String tipoMoneda;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_medio", nullable = false)
    private MedioPago medio;

    @Column(name = "fecha_pago")
    private Instant fechaPago;

    @Column(name = "fecha_vencimiento")
    private Instant fechaVencimiento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_contrato", nullable = false)
    private Contrato contrato;

}