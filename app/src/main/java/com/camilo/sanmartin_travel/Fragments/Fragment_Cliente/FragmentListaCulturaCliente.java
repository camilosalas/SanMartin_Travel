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

import com.camilo.sanmartin_travel.Adapter.AdapterCultura;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleCultura;
import com.camilo.sanmartin_travel.Models.ListaCulturas;
import com.camilo.sanmartin_travel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FragmentListaCulturaCliente extends Fragment {

    View vista;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    ListView listCultucli;
    List<ListaCulturas> listCulturacli;


    public FragmentListaCulturaCliente() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_lista_cultura_cliente, container, false);

        listCultucli = vista.findViewById(R.id.listCultucli);

        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child
                ("ExperienciasCulturas");

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<ListaCulturas> lista) {
                listCultucli.setAdapter(new AdapterCultura(getActivity(), lista));
            }
        });

        listCultucli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListaCulturas culturas = (ListaCulturas) parent.getItemAtPosition(position);

                Fragment nuevofragment = new FragmentDetalleCultura();

                Bundle args = new Bundle();
                args.putString("Nombre", culturas.getNombreCultura());
                args.putString("Id", culturas.getKey());
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
                listCulturacli = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String key = snapshot.getKey();
                    String nombreCultura = snapshot.child("nombreCultura").getValue(String
                            .class);
                    String descripcion = snapshot.child("descripcion").getValue(String.class);
                    String img = snapshot.child("img").getValue(String.class);
                    listCulturacli.add(new ListaCulturas(key, nombreCultura, descripcion, img));
                }
                firebaseCallback.onCallback(listCulturacli);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "No se pudo cargar los datos.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private interface FirebaseCallback{
        void onCallback(List<ListaCulturas> lista);
    }


}
