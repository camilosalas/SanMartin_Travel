package com.camilo.sanmartin_travel.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ListaHoteles {

    private String key, NombreHotel, DescripcionHotel, imgHotel, telefonoHotel, ubicacion, precio;
    Double lat, lng;

    public ListaHoteles(String key, String nombreHotel, String descripcionHotel, String imgHotel, String telefonoHotel, String ubicacion, String precio, Double lat, Double lng) {
        this.key = key;
        NombreHotel = nombreHotel;
        DescripcionHotel = descripcionHotel;
        this.imgHotel = imgHotel;
        this.telefonoHotel = telefonoHotel;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.lat = lat;
        this.lng = lng;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

        try{
            byte[] bytecode = Base64.decode(imgHotel,Base64.DEFAULT);

            int alto = 100;//alto en pixeles;
            int ancho = 100;//ancho en pixeles;

            Bitmap foto = BitmapFactory.decodeByteArray(bytecode, 0, bytecode.length);
            this.imgHotel = String.valueOf(Bitmap.createScaledBitmap(foto,alto,ancho,true));

        }catch (Exception e){
            e.printStackTrace();
        }
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
