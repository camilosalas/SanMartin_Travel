package com.camilo.sanmartin_travel.Models;

public class NaturalExperiencias {

    private String NombreNatural, Descripcion,img;


    public NaturalExperiencias(String nombreNatural, String descripcion, String img) {
        NombreNatural = nombreNatural;
        Descripcion = descripcion;
        this.img = img;
    }

    public String getNombreNatural() {
        return NombreNatural;
    }

    public void setNombreNatural(String nombreNatural) {
        NombreNatural = nombreNatural;
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
