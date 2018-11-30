package com.camilo.sanmartin_travel.Fragments.Fragment_Cliente;

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

import com.camilo.sanmartin_travel.Adapter.AdapterComidaGastronomica;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleComidaGastronomica;
import com.camilo.sanmartin_travel.Models.ListaComidaGastronomica;
import com.camilo.sanmartin_travel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentListaComidaGastromicaCliente extends Fragment {

    View vista;

    private DatabaseReference mDatabase;

    ListView ListaComida;
    List<ListaComidaGastronomica> listcomida;

    public FragmentListaComidaGastromicaCliente() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_lista_comida_gastromica_cliente, container, false);

        ListaComida = vista.findViewById(R.id.comidaGastronomica);

        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("ComidaGastronomia");

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<ListaComidaGastronomica> lista) {
                ListaComida.setAdapter(new AdapterComidaGastronomica(getActivity(), lista));
            }
        });


        ListaComida.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListaComidaGastronomica comida = (ListaComidaGastronomica) parent.getItemAtPosition(position);

                Fragment nuevofragment = new FragmentDetalleComidaGastronomica(comida);

                Bundle args = new Bundle();
                args.putString("Nombre", comida.getNombreGastronomia());
                args.putString("Id", comida.getKey());
                nuevofragment.setArguments(args);

                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.contenedor, nuevofragment);
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
                listcomida = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String key = snapshot.getKey();
                    String nombreGastronomia = snapshot.child("nombreGastronomia").getValue(String.class);
                    String descripcion = snapshot.child("descripcion").getValue(String.class);
                    String img = snapshot.child("img").getValue(String.class);
                    listcomida.add(new ListaComidaGastronomica(key, nombreGastronomia, descripcion, img));
                }
                firebaseCallback.onCallback(listcomida);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "No se pudo cargar los datos.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private interface FirebaseCallback{
        void onCallback(List<ListaComidaGastronomica> lista);
    }



}
