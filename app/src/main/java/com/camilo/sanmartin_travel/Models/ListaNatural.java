package com.camilo.sanmartin_travel.Models;

public class ListaNatural {

    private String key, NombreNatural, Descripcion,img;

    public ListaNatural(String key, String nombreNatural, String descripcion, String img) {
        this.key = key;
        NombreNatural = nombreNatural;
        Descripcion = descripcion;
        this.img = img;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
