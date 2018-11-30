package com.camilo.sanmartin_travel.Models;

public class ListaAventuras {


    private String key, NombreAventura, Descripcion,img;

    public ListaAventuras(String key, String nombreAventura, String descripcion, String img) {
        this.key = key;
        NombreAventura = nombreAventura;
        Descripcion = descripcion;
        this.img = img;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
