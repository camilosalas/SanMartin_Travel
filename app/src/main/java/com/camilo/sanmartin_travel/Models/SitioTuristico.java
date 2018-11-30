package com.camilo.sanmartin_travel.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class SitioTuristico {

    private String NombreLugar, Descripcion, img;
    private Double lat, lng;

    public SitioTuristico() {
    }

    public SitioTuristico(String nombreLugar, String descripcion, String img, Double lat, Double lng) {
        NombreLugar = nombreLugar;
        Descripcion = descripcion;
        this.img = img;
        this.lat = lat;
        this.lng = lng;
    }

    public String getNombreLugar() {
        return NombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        NombreLugar = nombreLugar;
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
