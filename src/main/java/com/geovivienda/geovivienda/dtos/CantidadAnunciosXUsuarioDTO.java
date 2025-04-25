package com.geovivienda.geovivienda.dtos;

public class CantidadAnunciosXUsuarioDTO {

    private String nombreUsuario;
    private int cantidadAnuncios;

    /*public CantidadAnunciosXUsuarioDTO(String nombreUsuario, Long cantidadAnuncios) {
        this.nombreUsuario = nombreUsuario;
        this.cantidadAnuncios = cantidadAnuncios;
    }*/

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getCantidadAnuncios() {
        return cantidadAnuncios;
    }

    public void setCantidadAnuncios(int cantidadAnuncios) {
        this.cantidadAnuncios = cantidadAnuncios;
    }
}
