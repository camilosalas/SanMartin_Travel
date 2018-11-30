package com.camilo.sanmartin_travel.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ListaSitios {

    private String key, NombreLugar, Descripcion, img;
    Double lat, lng;

    public ListaSitios(String key, String nombreLugar, String descripcion, String img, Double lat, Double lng) {
        this.key = key;
        NombreLugar = nombreLugar;
        Descripcion = descripcion;
        this.img = img;
        this.lat = lat;
        this.lng = lng;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

        try{
            byte[] bytecode = Base64.decode(img,Base64.DEFAULT);

            int alto = 50;//alto en pixeles;
            int ancho = 50;//ancho en pixeles;

            Bitmap foto = BitmapFactory.decodeByteArray(bytecode, 0, bytecode.length);
            this.img = String.valueOf(Bitmap.createScaledBitmap(foto,alto,ancho,true));

        }catch (Exception e){
            e.printStackTrace();
        }
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
