package com.camilo.sanmartin_travel.Models;

public class Paisaje {

    private String NombrePaisaje, DescripcionPaisaje, imgPaisaje, sitioUbicado;
    private Double lat, lng;


    public Paisaje(String nombrePaisaje, String descripcionPaisaje, String imgPaisaje, String sitioUbicado, Double lat, Double lng) {
        NombrePaisaje = nombrePaisaje;
        DescripcionPaisaje = descripcionPaisaje;
        this.imgPaisaje = imgPaisaje;
        this.sitioUbicado = sitioUbicado;
        this.lat = lat;
        this.lng = lng;
    }


    public String getNombrePaisaje() {
        return NombrePaisaje;
    }

    public void setNombrePaisaje(String nombrePaisaje) {
        NombrePaisaje = nombrePaisaje;
    }

    public String getDescripcionPaisaje() {
        return DescripcionPaisaje;
    }

    public void setDescripcionPaisaje(String descripcionPaisaje) {
        DescripcionPaisaje = descripcionPaisaje;
    }

    public String getImgPaisaje() {
        return imgPaisaje;
    }

    public void setImgPaisaje(String imgPaisaje) {
        this.imgPaisaje = imgPaisaje;
    }

    public String getSitioUbicado() {
        return sitioUbicado;
    }

    public void setSitioUbicado(String sitioUbicado) {
        this.sitioUbicado = sitioUbicado;
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
