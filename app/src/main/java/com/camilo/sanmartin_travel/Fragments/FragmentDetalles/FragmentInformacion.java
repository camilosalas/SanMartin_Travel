package com.camilo.sanmartin_travel.Fragments.FragmentDetalles;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.camilo.sanmartin_travel.Models.ListaSitios;
import com.camilo.sanmartin_travel.R;

public class FragmentInformacion extends Fragment {

    View vista;

    TextView nombresitio, descripcionsitio;

    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;


    ListaSitios listaSitios;

    @SuppressLint("ValidFragment")
    public FragmentInformacion(ListaSitios listaSitios) {
        this.listaSitios = listaSitios;
    }

    public FragmentInformacion(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_informacion, container, false);


        nombresitio = vista.findViewById(R.id.nombresitio);
        descripcionsitio = vista.findViewById(R.id.descripcionsitio);



        nombresitio.setText(listaSitios.getNombreLugar());
        descripcionsitio.setText(listaSitios.getDescripcion());


        return vista;
    }

}