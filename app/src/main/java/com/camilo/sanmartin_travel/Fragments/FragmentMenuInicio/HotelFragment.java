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

import com.camilo.sanmartin_travel.Adapter.AdapterHoteles;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleHotel;
import com.camilo.sanmartin_travel.Models.ListaHoteles;
import com.camilo.sanmartin_travel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HotelFragment extends Fragment {

    View vista;

    ListView LVHotelcli;
    List<ListaHoteles> listHotelcli;

    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_hotel, container, false);

        LVHotelcli = vista.findViewById(R.id.LVHotelcli);

        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("Hoteles");

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<ListaHoteles> lista) {
                LVHotelcli.setAdapter(new AdapterHoteles(getActivity(), lista));
            }
        });

        LVHotelcli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListaHoteles hotel = (ListaHoteles) parent.getItemAtPosition(position);

                Fragment nuevofragmet = new FragmentDetalleHotel(hotel);

                Bundle args = new Bundle();
                args.putString("Nombre", hotel.getNombreHotel());
                args.putString("Id", hotel.getKey());
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
                listHotelcli = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String key = snapshot.getKey();
                    String nombreHotel = snapshot.child("nombreHotel").getValue(String.class);
                    String descripcion = snapshot.child("descripcionHotel").getValue(String.class);
                    String img = snapshot.child("imgHotel").getValue(String.class);
                    String telefono = snapshot.child("telefonoHotel").getValue(String.class);
                    String ubicacion = snapshot.child("ubicacion").getValue(String.class);
                    String precio = snapshot.child("precio").getValue(String.class);
                    Double lat = snapshot.child("lat").getValue(Double.class);
                    Double lng = snapshot.child("lng").getValue(Double.class);

                    listHotelcli.add(new ListaHoteles(key, nombreHotel, descripcion, img, telefono, ubicacion, precio,  lat, lng));
                }
                firebaseCallback.onCallback(listHotelcli);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "No se pudo cargar los datos.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private interface FirebaseCallback{
        void onCallback(List<ListaHoteles> lista);
    }

}
