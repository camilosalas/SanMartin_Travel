package com.camilo.sanmartin_travel.Fragments.Fragment_Cliente;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.camilo.sanmartin_travel.Fragments.Fragment_Administrador.FragmentListaComidaGastronomia;
import com.camilo.sanmartin_travel.Fragments.Fragment_Administrador.FragmentListaFrutaGastronomica;
import com.camilo.sanmartin_travel.R;


public class FragmentInicioGastronimiaAdmin extends Fragment {

    View vista;

    ImageButton comida, fruta;

    public FragmentInicioGastronimiaAdmin() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_inicio_gastronimia_admin, container, false);

        comida = vista.findViewById(R.id.Comidas);
        comida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment comidagastro = new FragmentListaComidaGastronomia();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, comidagastro);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        fruta = vista.findViewById(R.id.Frutas);
        fruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment frutagastro = new FragmentListaFrutaGastronomica();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, frutagastro);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return vista;
    }


}
