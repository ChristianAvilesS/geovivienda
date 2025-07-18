package com.geovivienda.geovivienda.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "contratos")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "id_vendedor", referencedColumnName = "id_usuario", nullable = false)
    private Usuario vendedor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "id_inmueble", nullable = false)
    private Inmueble inmueble;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "id_comprador", referencedColumnName = "id_usuario", nullable = false)
    private Usuario comprador;

    @Column(name = "tipo_contrato", length = 200)
    private String tipoContrato;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "monto_total", precision = 11, scale = 3)
    private BigDecimal montoTotal;

    @Column(name = "fecha_firma")
    private Instant fechaFirma;

    @Column(name = "fecha_vencimiento")
    private Instant fechaVencimiento;
}