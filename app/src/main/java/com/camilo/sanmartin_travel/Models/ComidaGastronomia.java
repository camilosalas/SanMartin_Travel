package com.camilo.sanmartin_travel.Models;

public class ComidaGastronomia {
    private String NombreGastronomia, Descripcion,img;

    public ComidaGastronomia() {
    }


    public ComidaGastronomia(String nombreGastronomia, String descripcion, String img) {
        NombreGastronomia = nombreGastronomia;
        Descripcion = descripcion;
        this.img = img;
    }


    public String getNombreGastronomia() {
        return NombreGastronomia;
    }

    public void setNombreGastronomia(String nombreGastronomia) {
        NombreGastronomia = nombreGastronomia;
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
