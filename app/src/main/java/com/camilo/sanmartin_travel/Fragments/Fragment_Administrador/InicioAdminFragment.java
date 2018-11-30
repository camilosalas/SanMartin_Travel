package com.camilo.sanmartin_travel.Fragments.Fragment_Administrador;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.camilo.sanmartin_travel.Fragments.Fragment_Cliente.FragmentInicioGastronimiaAdmin;
import com.camilo.sanmartin_travel.R;


public class InicioAdminFragment extends Fragment {

    View vista;

    public InicioAdminFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_inicio_admin, container, false);

        ImageButton destino = vista.findViewById(R.id.Destinos);
        destino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment centrosTuristicosfragmet = new FragmentListaCentrosTuristicos();
                assert  getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, centrosTuristicosfragmet);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        ImageButton gastronomia = vista.findViewById(R.id.Gastronomia);
        gastronomia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment gastronomiafragment = new FragmentInicioGastronimiaAdmin();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, gastronomiafragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        ImageButton paisaje = vista.findViewById(R.id.Galeria);
        paisaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment paisajefragment = new FragmentListaPaisaje();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, paisajefragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        ImageButton hotel = vista.findViewById(R.id.Hoteles);
        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment hotelfragment = new FragmentListaHotel();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, hotelfragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ImageButton experiencia = vista.findViewById(R.id.Experiencia);
        experiencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment experienciafragment = new FragmentListaExperiencia();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, experienciafragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return vista;
    }

}
