package com.geovivienda.geovivienda.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "medios_pago")
public class MedioPago {
    @Id
    @Column(name = "id_medio", nullable = false)
    private Integer id;

    @Column(name = "medio_pago", length = 20)
    private String medioPago;

}