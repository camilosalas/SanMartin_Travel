package com.camilo.sanmartin_travel.Fragments.FragmentMenuInicio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.camilo.sanmartin_travel.Adapter.AdapterSitioTuristico;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleSitioCliente;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalles.FragmentDetallesSitio;
import com.camilo.sanmartin_travel.Models.ListaSitios;
import com.camilo.sanmartin_travel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Destino_Fragment extends Fragment {

    View vista;

    private DatabaseReference mDatabase;

    ListView ListaDestino;
    List<ListaSitios> listSitios;

    public Destino_Fragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_destino_, container, false);

        ListaDestino = vista.findViewById(R.id.ListaDestino);

        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("SitiosTuristicos");

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<ListaSitios> lista) {
                ListaDestino.setAdapter(new AdapterSitioTuristico(getActivity(), lista));
            }
        });

        ListaDestino.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListaSitios sitios = (ListaSitios) parent.getItemAtPosition(position);

                Fragment nuevofragmet = new FragmentDetalleSitioCliente(sitios);

                Bundle args = new Bundle();
                args.putString("Nombre", sitios.getNombreLugar());
                args.putString("Id", sitios.getKey());
                nuevofragmet.setArguments(args);

                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.contenedor, nuevofragmet);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return vista;
    }

    private void readData(final FirebaseCallback firebaseCallback){

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSitios = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String key = snapshot.getKey();
                    String nombreLugar = snapshot.child("nombreLugar").getValue(String.class);
                    String descripcion = snapshot.child("descripcion").getValue(String.class);
                    String img = snapshot.child("img").getValue(String.class);
                    Double lat = (snapshot.child("lat").getValue(Double.class));
                    Double lng = (snapshot.child("lng").getValue(Double.class));
                    listSitios.add(new ListaSitios(key, nombreLugar, descripcion, img, lat, lng));
                }
                firebaseCallback.onCallback(listSitios);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "No se pudo cargar los datos.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private interface FirebaseCallback{
        void onCallback(List<ListaSitios> lista);
    }
}
