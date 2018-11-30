package com.camilo.sanmartin_travel.Fragments.FragmentMenuInicio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.camilo.sanmartin_travel.Fragments.Fragment_Administrador.FragmentListaAventuras;
import com.camilo.sanmartin_travel.Fragments.Fragment_Administrador.FragmentListaCulturas;
import com.camilo.sanmartin_travel.Fragments.Fragment_Administrador.FragmentListaMilenario;
import com.camilo.sanmartin_travel.Fragments.Fragment_Administrador.FragmentListaNatural;
import com.camilo.sanmartin_travel.Fragments.Fragment_Cliente.FragmentExperiencias;
import com.camilo.sanmartin_travel.Fragments.Fragment_Cliente.FragmentListaAventuraCliente;
import com.camilo.sanmartin_travel.Fragments.Fragment_Cliente.FragmentListaCulturaCliente;
import com.camilo.sanmartin_travel.Fragments.Fragment_Cliente.FragmentListaMilenarioCliente;
import com.camilo.sanmartin_travel.Fragments.Fragment_Cliente.FragmentListaNaturalCliente;
import com.camilo.sanmartin_travel.R;


public class ExperienciaFragment extends Fragment {
    View vista;

    ImageView aventura,cultura,milenario,natural;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_experiencia, container, false);

        aventura = vista.findViewById(R.id.Aventuras);

        aventura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment aveturaExperiencia = new FragmentListaAventuraCliente();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, aveturaExperiencia);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        cultura = vista.findViewById(R.id.Culturas);

        cultura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment culturaExperiencia = new FragmentListaCulturaCliente();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, culturaExperiencia);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        milenario = vista.findViewById(R.id.Milenarios);

        milenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment milenarioExperiencia = new FragmentListaMilenarioCliente();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, milenarioExperiencia);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        natural = vista.findViewById(R.id.Naturals);

        natural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment naturalExperiencia = new FragmentListaNaturalCliente();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, naturalExperiencia);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return vista;
    }
}
