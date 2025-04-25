package com.geovivienda.geovivienda.dtos;

import com.geovivienda.geovivienda.entities.Inmueble;
import com.geovivienda.geovivienda.entities.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnuncioDTO {
    private Integer idAnuncio;
    private Usuario anunciante;
    private String descripcion;
    private LocalDateTime fechaPublicacion;
    private Inmueble inmueble;
}
