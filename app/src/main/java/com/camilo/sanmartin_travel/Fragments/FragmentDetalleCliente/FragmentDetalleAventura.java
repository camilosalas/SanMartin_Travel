package com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.camilo.sanmartin_travel.Models.ListaAventuras;
import com.camilo.sanmartin_travel.R;


public class FragmentDetalleAventura extends Fragment {


    View vista;

    TextView nombreAventd, descripcionAventd;


    ListaAventuras listaAventuras;

    @SuppressLint("ValidFragment")
    public FragmentDetalleAventura(ListaAventuras listaAventuras) {
        this.listaAventuras = listaAventuras;
    }


    public FragmentDetalleAventura(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_detalle_aventura, container, false);



        return vista;
    }
}
