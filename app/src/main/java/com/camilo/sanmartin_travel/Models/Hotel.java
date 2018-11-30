package com.camilo.sanmartin_travel.Models;

public class Hotel {

    private String NombreHotel, DescripcionHotel, imgHotel, telefonoHotel, ubicacion, precio;
    private Double lat, lng;

    public Hotel(String nombreHotel, String descripcionHotel, String imgHotel, String telefonoHotel, String ubicacion, String precio, Double lat, Double lng) {
        NombreHotel = nombreHotel;
        DescripcionHotel = descripcionHotel;
        this.imgHotel = imgHotel;
        this.telefonoHotel = telefonoHotel;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.lat = lat;
        this.lng = lng;
    }

    public String getNombreHotel() {
        return NombreHotel;
    }

    public void setNombreHotel(String nombreHotel) {
        NombreHotel = nombreHotel;
    }

    public String getDescripcionHotel() {
        return DescripcionHotel;
    }

    public void setDescripcionHotel(String descripcionHotel) {
        DescripcionHotel = descripcionHotel;
    }

    public String getImgHotel() {
        return imgHotel;
    }

    public void setImgHotel(String imgHotel) {
        this.imgHotel = imgHotel;
    }

    public String getTelefonoHotel() {
        return telefonoHotel;
    }

    public void setTelefonoHotel(String telefonoHotel) {
        this.telefonoHotel = telefonoHotel;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
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
