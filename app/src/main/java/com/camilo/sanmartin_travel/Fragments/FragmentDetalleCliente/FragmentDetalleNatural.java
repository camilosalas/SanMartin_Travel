package com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.camilo.sanmartin_travel.R;

public class FragmentDetalleNatural extends Fragment {


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalle_natural, container, false);


        return view;
    }
}
