package com.geovivienda.geovivienda.dtos;

import com.geovivienda.geovivienda.entities.Inmueble;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImagenDTO {
    private Integer idImagen;
    private Inmueble inmueble;
    private String url;
    private String titulo;
}
