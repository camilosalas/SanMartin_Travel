package com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.camilo.sanmartin_travel.R;

public class FragmentDetalleCultura extends Fragment {

    View vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_detalle_cultura, container, false);


        return vista;
    }
}
