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

import com.camilo.sanmartin_travel.Adapter.AdapterCultura;
import com.camilo.sanmartin_travel.Adapter.AdapterMilenario;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleCultura;
import com.camilo.sanmartin_travel.Models.CulturaExperiencias;
import com.camilo.sanmartin_travel.Models.ListaMilenario;
import com.camilo.sanmartin_travel.Models.MilenarioExperiencias;
import com.camilo.sanmartin_travel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FragmentListaMilenario extends Fragment {

    View vista;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    ListView listMilen;
    List<ListaMilenario> listMilenario;

    public FragmentListaMilenario(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_lista_milenario, container, false);


        listMilen = vista.findViewById(R.id.listMilen);

        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child
                ("ExperienciasMilenario");

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<ListaMilenario> lista) {
                listMilen.setAdapter(new AdapterMilenario(getActivity(), lista));
            }
        });

        listMilen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListaMilenario milenarios = (ListaMilenario) parent.getItemAtPosition(position);

                Fragment nuevofragment = new FragmentDetalleCultura();

                Bundle args = new Bundle();
                args.putString("Nombre", milenarios.getNombreMilenario());
                args.putString("Id", milenarios.getKey());
                nuevofragment.setArguments(args);

                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.principales, nuevofragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        listMilen.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final ListaMilenario milenarios = (ListaMilenario) parent.getItemAtPosition
                        (position);

                final CharSequence[] Opciones = {"Editar", "Eliminar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(milenarios.getNombreMilenario());
                builder.setIcon(R.drawable.menu);
                builder.setItems(Opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Opciones[which] == "Editar"){

                            AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
                            @SuppressLint("InflateParams") View mView = getLayoutInflater()
                                    .inflate(R.layout.dialog_edit_milenario,null);


                            final EditText nombre = mView.findViewById(R.id.NombreMilenarioEdit);
                            final EditText descripcion = mView.findViewById(R.id
                                    .DescripcionMilenarioEdit);
                            CardView edit = mView.findViewById(R.id.EditarMilenarioExperiencia);

                            nombre.setText(milenarios.getNombreMilenario());
                            descripcion.setText(milenarios.getDescripcion());
                            final String img = milenarios.getImg();

                            mbuilder.setView(mView);
                            final AlertDialog dialogo = mbuilder.create();

                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(!nombre.getText().toString().equals("") && !descripcion.getText().toString().equals("")){

                                        MilenarioExperiencias cultura = new MilenarioExperiencias
                                                (nombre.getText().toString(), descripcion.getText().toString(), img);
                                        mDatabase.child(milenarios.getKey()).setValue(cultura);

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
                            nombre.setText(milenarios.getNombreMilenario());

                            final CardView eliminar = mView.findViewById(R.id.EliminarSitioTuristico);

                            mbuilder.setView(mView);
                            final AlertDialog dialogo = mbuilder.create();

                            eliminar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDatabase.child(milenarios.getKey()).removeValue();
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

        FloatingActionButton fab = vista.findViewById(R.id.fabmilenario);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment agregarMil = new FragmentAgregarMilenario();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, agregarMil);
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
                listMilenario = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String key = snapshot.getKey();
                    String nombreMilenario = snapshot.child("nombreMilenario").getValue(String
                            .class);
                    String descripcion = snapshot.child("descripcion").getValue(String.class);
                    String img = snapshot.child("img").getValue(String.class);
                    listMilenario.add(new ListaMilenario(key, nombreMilenario, descripcion, img));
                }
                firebaseCallback.onCallback(listMilenario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "No se pudo cargar los datos.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private interface FirebaseCallback{
        void onCallback(List<ListaMilenario> lista);
    }
}
