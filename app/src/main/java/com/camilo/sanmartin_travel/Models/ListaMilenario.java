package com.camilo.sanmartin_travel.Models;

public class ListaMilenario {
    private String key, NombreMilenario, Descripcion,img;

    public ListaMilenario(String key, String nombreMilenario, String descripcion, String img) {
        this.key = key;
        NombreMilenario = nombreMilenario;
        Descripcion = descripcion;
        this.img = img;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNombreMilenario() {
        return NombreMilenario;
    }

    public void setNombreMilenario(String nombreMilenario) {
        NombreMilenario = nombreMilenario;
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
