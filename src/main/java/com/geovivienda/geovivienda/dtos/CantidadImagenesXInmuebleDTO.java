package com.geovivienda.geovivienda.dtos;

public class CantidadImagenesXInmuebleDTO {
    private int cantidadImagenes;
    private String nombreInmueble;

    public int getCantidadImagenes() {
        return cantidadImagenes;
    }

    public void setCantidadImagenes(int cantidadImagenes) {
        this.cantidadImagenes = cantidadImagenes;
    }

    public String getNombreInmueble() {
        return nombreInmueble;
    }

    public void setNombreInmueble(String nombreInmueble) {
        this.nombreInmueble = nombreInmueble;
    }
}
