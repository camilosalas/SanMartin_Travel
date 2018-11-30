package com.camilo.sanmartin_travel.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ListaComidaGastronomica{

    private String key, NombreGastronomia, Descripcion,img;

    public ListaComidaGastronomica(String key, String nombreGastronomia, String descripcion, String img) {
        this.key = key;
        NombreGastronomia = nombreGastronomia;
        Descripcion = descripcion;
        this.img = img;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

        try{
            byte[] bytecode = Base64.decode(img,Base64.DEFAULT);

            int alto = 100;//alto en pixeles;
            int ancho = 100;//ancho en pixeles;

            Bitmap foto = BitmapFactory.decodeByteArray(bytecode, 0, bytecode.length);
            this.img = String.valueOf(Bitmap.createScaledBitmap(foto,alto,ancho,true));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
