package com.camilo.sanmartin_travel.Fragments.FragmentMenuInicio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.camilo.sanmartin_travel.Fragments.Fragment_Administrador.FragmentListaComidaGastronomia;
import com.camilo.sanmartin_travel.Fragments.Fragment_Administrador.FragmentListaFrutaGastronomica;
import com.camilo.sanmartin_travel.Fragments.Fragment_Cliente.FragmentListaComidaGastromicaCliente;
import com.camilo.sanmartin_travel.Fragments.Fragment_Cliente.FragmentListaFrutaGastronomiaCliente;
import com.camilo.sanmartin_travel.R;


public class GastronomiaFragment extends Fragment {

    View vista;

    ImageView comida, fruta;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_gastronomia, container, false);

        comida = vista.findViewById(R.id.cocina);
        fruta = vista.findViewById(R.id.fruta);

        comida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment cocinaGastronomica = new FragmentListaComidaGastromicaCliente();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, cocinaGastronomica);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


       fruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment frutaGastronomica = new FragmentListaFrutaGastronomiaCliente();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, frutaGastronomica);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });




        return vista;
    }

}
