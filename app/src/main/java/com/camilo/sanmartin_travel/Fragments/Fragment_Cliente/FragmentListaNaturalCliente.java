package com.camilo.sanmartin_travel.Fragments.Fragment_Cliente;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.camilo.sanmartin_travel.R;

public class FragmentListaNaturalCliente extends Fragment {
    View vista;


    public FragmentListaNaturalCliente() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_lista_natural_cliente, container, false);

        return vista;
    }


}
