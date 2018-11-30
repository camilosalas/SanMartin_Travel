package com.camilo.sanmartin_travel.Models;

public class CulturaExperiencias {
    private String NombreCultura, Descripcion,img;

    public CulturaExperiencias(String nombreCultura, String descripcion, String img) {
        NombreCultura = nombreCultura;
        Descripcion = descripcion;
        this.img = img;
    }

    public String getNombreCultura() {
        return NombreCultura;
    }

    public void setNombreCultura(String nombreCultura) {
        NombreCultura = nombreCultura;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
