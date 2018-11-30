package com.camilo.sanmartin_travel.Models;

public class AventurasExperiencias {
    private String NombreAventura, Descripcion,img;

    public AventurasExperiencias(String nombreAventura, String descripcion, String img) {
        NombreAventura = nombreAventura;
        Descripcion = descripcion;
        this.img = img;
    }

    public String getNombreAventura() {
        return NombreAventura;
    }

    public void setNombreAventura(String nombreAventura) {
        NombreAventura = nombreAventura;
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
