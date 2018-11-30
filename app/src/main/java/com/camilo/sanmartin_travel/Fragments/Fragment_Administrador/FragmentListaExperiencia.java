package com.camilo.sanmartin_travel.Fragments.Fragment_Administrador;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleAventura;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleCultura;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleMilenario;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleNatural;
import com.camilo.sanmartin_travel.R;


public class FragmentListaExperiencia extends Fragment {

    View vista;

    ImageButton cultura, milenario,aventura,natural;


    public FragmentListaExperiencia() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_lista_experiencia, container, false);

        cultura = vista.findViewById(R.id.Cultura);
        cultura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment culturaex = new FragmentListaCulturas();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, culturaex);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        milenario = vista.findViewById(R.id.Milenario);
        milenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment listmilen = new FragmentListaMilenario();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, listmilen);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        aventura = vista.findViewById(R.id.Aventura);
        aventura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment aventuraexper = new FragmentListaAventuras();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, aventuraexper);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        natural = vista.findViewById(R.id.Natural);
        natural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment naturaa = new FragmentListaNatural();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, naturaa);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return vista;
    }

}
