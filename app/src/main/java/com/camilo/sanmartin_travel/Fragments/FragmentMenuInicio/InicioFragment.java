package com.camilo.sanmartin_travel.Fragments.FragmentMenuInicio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.camilo.sanmartin_travel.R;


public class InicioFragment extends Fragment {

    View vista;

    ImageView imvDestino, imvGastronomia, imvExperiencia, imvGaleria, imvHotel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_inicio, container, false);

        imvDestino = vista.findViewById(R.id.destino);
        imvDestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment nuevodestino = new Destino_Fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, nuevodestino);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });


        imvGastronomia = vista.findViewById(R.id.gastronomia);
        imvGastronomia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment nuevogastronomia = new GastronomiaFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, nuevogastronomia);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        imvExperiencia = vista.findViewById(R.id.experiencia);
        imvExperiencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment nuevoexperiencia = new ExperienciaFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, nuevoexperiencia);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });


        imvGaleria = vista.findViewById(R.id.galeria);
        imvGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment nuevogaleria = new PaisajeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, nuevogaleria);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        imvHotel = vista.findViewById(R.id.hotel);
        imvHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment nuevoutilidad = new HotelFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, nuevoutilidad);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return vista;
    }
}
