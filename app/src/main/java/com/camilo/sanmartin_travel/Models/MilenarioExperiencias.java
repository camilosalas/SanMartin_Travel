package com.camilo.sanmartin_travel.Models;

public class MilenarioExperiencias {

    private String NombreMilenario, Descripcion,img;

    public MilenarioExperiencias(String nombreMilenario, String descripcion, String img) {
        NombreMilenario = nombreMilenario;
        Descripcion = descripcion;
        this.img = img;
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
