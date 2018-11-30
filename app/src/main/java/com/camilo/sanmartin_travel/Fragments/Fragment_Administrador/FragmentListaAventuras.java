package com.camilo.sanmartin_travel.Fragments.Fragment_Administrador;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.camilo.sanmartin_travel.Adapter.AdapterAventura;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleAventura;
import com.camilo.sanmartin_travel.Models.AventurasExperiencias;
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

public class FragmentListaAventuras extends Fragment {

    View vista;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    ListView ListAvent;
    List<ListaAventuras> listAventura;

    public FragmentListaAventuras(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_lista_aventuras, container, false);


        ListAvent = vista.findViewById(R.id.ListAvent);

        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("ExperienciasAventuras");

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<ListaAventuras> lista) {
                ListAvent.setAdapter(new AdapterAventura(getActivity(), lista));
            }
        });

        ListAvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                transaction.replace(R.id.principales, nuevofragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        ListAvent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final ListaAventuras aventurass = (ListaAventuras) parent.getItemAtPosition
                        (position);

                final CharSequence[] Opciones = {"Editar", "Eliminar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(aventurass.getNombreAventura());
                builder.setIcon(R.drawable.menu);
                builder.setItems(Opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Opciones[which] == "Editar"){

                            AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
                            @SuppressLint("InflateParams") View mView = getLayoutInflater().inflate(R.layout.dialog_edit_aventur,null);


                            final EditText nombre = mView.findViewById(R.id.NombreAventuraEdit);
                            final EditText descripcion = mView.findViewById(R.id
                                    .DescripcionAventuraEdit);
                            CardView edit = mView.findViewById(R.id.EditarAventuraExperiencia);

                            nombre.setText(aventurass.getNombreAventura());
                            descripcion.setText(aventurass.getDescripcion());
                            final String img = aventurass.getImg();

                            mbuilder.setView(mView);
                            final AlertDialog dialogo = mbuilder.create();

                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(!nombre.getText().toString().equals("") && !descripcion.getText().toString().equals("")){

                                        AventurasExperiencias aventuras = new AventurasExperiencias(nombre
                                                .getText().toString(), descripcion.getText().toString(), img);
                                        mDatabase.child(aventurass.getKey()).setValue(aventuras);

                                        dialogo.dismiss();
                                    } else{

                                        Toast.makeText(getActivity(), "Por favor rellene el formulario para continuar", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                            dialogo.show();
                        }else if(Opciones[which] == "Eliminar"){
                            AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
                            @SuppressLint("InflateParams") View mView = getLayoutInflater().inflate(R.layout.dialog_delete,null);

                            final TextView nombre = mView.findViewById(R.id.nombreSiitioEliminar);
                            nombre.setText(aventurass.getNombreAventura());

                            final CardView eliminar = mView.findViewById(R.id.EliminarSitioTuristico);

                            mbuilder.setView(mView);
                            final AlertDialog dialogo = mbuilder.create();

                            eliminar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDatabase.child(aventurass.getKey()).removeValue();
                                    dialogo.dismiss();
                                }
                            });

                            dialogo.show();
                        }
                    }
                });

                builder.show();
                return true;
            }
        });

        FloatingActionButton fab = vista.findViewById(R.id.fabaventuras);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment agregarAvent = new FragmentAgregarAventura();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, agregarAvent);
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
                listAventura = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String key = snapshot.getKey();
                    String nombreAventura = snapshot.child("nombreAventura").getValue(String
                            .class);
                    String descripcion = snapshot.child("descripcion").getValue(String.class);
                    String img = snapshot.child("img").getValue(String.class);
                    listAventura.add(new ListaAventuras(key, nombreAventura, descripcion, img));
                }
                firebaseCallback.onCallback(listAventura);
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
