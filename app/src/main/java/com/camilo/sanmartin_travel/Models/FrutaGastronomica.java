package com.camilo.sanmartin_travel.Models;

public class FrutaGastronomica {

    private String NombreFruta, Descripcion,img;

    public FrutaGastronomica(){}

    public FrutaGastronomica(String nombreFruta, String descripcion, String img) {
        NombreFruta = nombreFruta;
        Descripcion = descripcion;
        this.img = img;
    }

    public String getNombreFruta() {
        return NombreFruta;
    }

    public void setNombreFruta(String nombreFruta) {
        NombreFruta = nombreFruta;
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
