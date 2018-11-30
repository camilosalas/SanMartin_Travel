package com.camilo.sanmartin_travel.Fragments.Fragment_Cliente;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.camilo.sanmartin_travel.Models.ListaMilenario;
import com.camilo.sanmartin_travel.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class FragmentListaMilenarioCliente extends Fragment {

    View vista;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    ListView listMilencli;
    List<ListaMilenario> listMilenarioCli;


    public FragmentListaMilenarioCliente() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        vista=  inflater.inflate(R.layout.fragment_lista_milenario_cliente, container, false);


        listMilencli = vista.findViewById(R.id.listMilenCli);

        return vista;
    }


}
