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

import com.camilo.sanmartin_travel.Adapter.AdapterPaisaje;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetallePaisajeCliente;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleSitioCliente;
import com.camilo.sanmartin_travel.Models.ListaPaisaje;
import com.camilo.sanmartin_travel.Models.ListaSitios;
import com.camilo.sanmartin_travel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PaisajeFragment extends Fragment {

    View vista;

    private DatabaseReference mDatabase;

    ListView listapaisaje;
    List<ListaPaisaje> listpaisa;

    public PaisajeFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_paisaje, container, false);

        listapaisaje = vista.findViewById(R.id.ListaPaisaje);

        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("Paisajes");

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<ListaPaisaje> lista) {
                listapaisaje.setAdapter(new AdapterPaisaje(getActivity(), lista));
            }
        });

        listapaisaje.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListaPaisaje paisaje = (ListaPaisaje) parent.getItemAtPosition(position);

                Fragment nuevofragmet = new FragmentDetallePaisajeCliente(paisaje);

                Bundle args = new Bundle();
                args.putString("Nombre", paisaje.getNombrePaisaje());
                args.putString("Id", paisaje.getKey());
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
                listpaisa = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String key = snapshot.getKey();
                    String nombrePaisaje = snapshot.child("nombrePaisaje").getValue(String.class);
                    String descripcionPaisaje = snapshot.child("descripcionPaisaje").getValue(String.class);
                    String imgPaisaje = snapshot.child("imgPaisaje").getValue(String.class);
                    Double lat = (snapshot.child("lat").getValue(Double.class));
                    Double lng = (snapshot.child("lng").getValue(Double.class));
                    String ubicacionPaisaje = snapshot.child("sitioUbicado").getValue(String.class);
                    listpaisa.add(new ListaPaisaje(key, nombrePaisaje, descripcionPaisaje, imgPaisaje, ubicacionPaisaje, lat, lng));
                }
                firebaseCallback.onCallback(listpaisa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "No se pudo cargar los datos.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private interface FirebaseCallback{
        void onCallback(List<ListaPaisaje> lista);
    }
}
