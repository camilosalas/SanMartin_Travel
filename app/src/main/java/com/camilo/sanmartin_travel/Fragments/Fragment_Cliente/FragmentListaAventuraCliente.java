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

import com.camilo.sanmartin_travel.Adapter.AdapterAventura;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleAventura;
import com.camilo.sanmartin_travel.Fragments.Fragment_Administrador.FragmentListaAventuras;
import com.camilo.sanmartin_travel.Models.ListaAventuras;
import com.camilo.sanmartin_travel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FragmentListaAventuraCliente extends Fragment {

    View vista;


    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    ListView listAventcli;
    List<ListaAventuras> listAventuracli;


    public FragmentListaAventuraCliente() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_lista_aventura_cliente, container, false);


        listAventcli = vista.findViewById(R.id.listAvencli);
        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("ExperienciasAventuras");

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<ListaAventuras> lista) {
                listAventcli.setAdapter(new AdapterAventura(getActivity(), lista));
            }
        });


        listAventcli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListaAventuras aventuraas = (ListaAventuras) parent.getItemAtPosition(position);

                Fragment nuevofragment = new FragmentDetalleAventura();

                Bundle args = new Bundle();
                args.putString("Nombre", aventuraas.getNombreAventura());
                args.putString("Id", aventuraas.getKey());
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
                listAventuracli = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String key = snapshot.getKey();
                    String nombreAventura = snapshot.child("nombreAventura").getValue(String
                            .class);
                    String descripcion = snapshot.child("descripcion").getValue(String.class);
                    String img = snapshot.child("img").getValue(String.class);
                    listAventuracli.add(new ListaAventuras(key, nombreAventura, descripcion, img));
                }
                firebaseCallback.onCallback(listAventuracli);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "No se pudo cargar los datos.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private interface FirebaseCallback{
        void onCallback(List<ListaAventuras> lista);
    }

}
