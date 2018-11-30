package com.camilo.sanmartin_travel.Models;

public class ListaCulturas {
    private String key, NombreCultura, Descripcion,img;

    public ListaCulturas(String key, String nombreCultura, String descripcion, String img) {
        this.key = key;
        NombreCultura = nombreCultura;
        Descripcion = descripcion;
        this.img = img;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
