package com.camilo.sanmartin_travel.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ListaPaisaje {

    private String key, NombrePaisaje, DescripcionPaisaje, imgPaisaje, sitioUbicado;
    private Double lat, lng;


    public ListaPaisaje(String key, String nombrePaisaje, String descripcionPaisaje, String imgPaisaje, String sitioUbicado, Double lat, Double lng) {
        this.key = key;
        NombrePaisaje = nombrePaisaje;
        DescripcionPaisaje = descripcionPaisaje;
        this.imgPaisaje = imgPaisaje;
        this.sitioUbicado = sitioUbicado;
        this.lat = lat;
        this.lng = lng;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

        try{
            byte[] bytecode = Base64.decode(imgPaisaje,Base64.DEFAULT);

            int alto = 100;//alto en pixeles;
            int ancho = 100;//ancho en pixeles;

            Bitmap foto = BitmapFactory.decodeByteArray(bytecode, 0, bytecode.length);
            this.imgPaisaje = String.valueOf(Bitmap.createScaledBitmap(foto,alto,ancho,true));

        }catch (Exception e){
            e.printStackTrace();
        }
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
