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

import com.camilo.sanmartin_travel.Adapter.AdapterFrutaGastronomica;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleFrutaGastronomica;
import com.camilo.sanmartin_travel.Fragments.Fragment_Administrador.FragmentListaFrutaGastronomica;
import com.camilo.sanmartin_travel.Models.ListaFrutaGastronomica;
import com.camilo.sanmartin_travel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentListaFrutaGastronomiaCliente extends Fragment {

    View vista;

    private DatabaseReference mDatabase;
    ListView ListFrutacli;
    List<ListaFrutaGastronomica> listFrutaGas;


    public FragmentListaFrutaGastronomiaCliente() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_lista_fruta_gastronomia_cliente, container, false);

        ListFrutacli = vista.findViewById(R.id.ListaFrutacli);
        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("FrutaGastronomia");

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<ListaFrutaGastronomica> lista) {
                ListFrutacli.setAdapter(new AdapterFrutaGastronomica(getActivity(), lista));
            }
        });

        ListFrutacli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListaFrutaGastronomica fruta = (ListaFrutaGastronomica) parent.getItemAtPosition(position);

                Fragment nuevofragment = new FragmentDetalleFrutaGastronomica(fruta);

                Bundle args = new Bundle();
                args.putString("Nombre", fruta.getNombreFruta());
                args.putString("Id", fruta.getKey());
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
                listFrutaGas = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String key = snapshot.getKey();
                    String nombreFruta = snapshot.child("nombreFruta").getValue(String.class);
                    String descripcion = snapshot.child("descripcion").getValue(String.class);
                    String img = snapshot.child("img").getValue(String.class);
                    listFrutaGas.add(new ListaFrutaGastronomica(key, nombreFruta, descripcion, img));
                }
                firebaseCallback.onCallback(listFrutaGas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "No se pudo cargar los datos.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private interface FirebaseCallback{
        void onCallback(List<ListaFrutaGastronomica> lista);
    }

}
