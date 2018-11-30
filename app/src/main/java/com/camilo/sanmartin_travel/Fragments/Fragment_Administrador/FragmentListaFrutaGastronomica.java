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

import com.camilo.sanmartin_travel.Adapter.AdapterComidaGastronomica;
import com.camilo.sanmartin_travel.Adapter.AdapterFrutaGastronomica;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleComidaGastronomica;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente.FragmentDetalleFrutaGastronomica;
import com.camilo.sanmartin_travel.Models.ComidaGastronomia;
import com.camilo.sanmartin_travel.Models.FrutaGastronomica;
import com.camilo.sanmartin_travel.Models.ListaComidaGastronomica;
import com.camilo.sanmartin_travel.Models.ListaFrutaGastronomica;
import com.camilo.sanmartin_travel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentListaFrutaGastronomica extends Fragment {

    View vista;

    private DatabaseReference mDatabase;
    ListView ListFrutaGastro;
    List<ListaFrutaGastronomica> listFrutaGas;

    public FragmentListaFrutaGastronomica() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_lista_fruta_gastronomica, container, false);

        ListFrutaGastro = vista.findViewById(R.id.ListaFrutaGastronomica);
        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("FrutaGastronomia");

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<ListaFrutaGastronomica> lista) {
                ListFrutaGastro.setAdapter(new AdapterFrutaGastronomica(getActivity(), lista));
            }
        });

        ListFrutaGastro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                transaction.replace(R.id.principales, nuevofragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        ListFrutaGastro.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final ListaFrutaGastronomica fruta = (ListaFrutaGastronomica) parent.getItemAtPosition(position);

                final CharSequence[] Opciones = {"Editar", "Eliminar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(fruta.getNombreFruta());
                builder.setIcon(R.drawable.menu);
                builder.setItems(Opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Opciones[which] == "Editar"){

                            AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
                            @SuppressLint("InflateParams") View mView = getLayoutInflater().inflate(R.layout.dialog_edit_fruta_gastronomica,null);


                            final EditText nombre = mView.findViewById(R.id.NombreFrutadit);
                            final EditText descripcion = mView.findViewById(R.id.DescripcionFrutaEdit);
                            CardView edit = mView.findViewById(R.id.EditarFrutaGastronomica);

                            nombre.setText(fruta.getNombreFruta());
                            descripcion.setText(fruta.getDescripcion());
                            final String img = fruta.getImg();

                            mbuilder.setView(mView);
                            final AlertDialog dialogo = mbuilder.create();

                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(!nombre.getText().toString().equals("") && !descripcion.getText().toString().equals("")){

                                        FrutaGastronomica frutagas = new FrutaGastronomica(nombre.getText().toString(), descripcion.getText().toString(), img);
                                        mDatabase.child(fruta.getKey()).setValue(frutagas);

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
                            nombre.setText(fruta.getNombreFruta());

                            final CardView eliminar = mView.findViewById(R.id.EliminarSitioTuristico);

                            mbuilder.setView(mView);
                            final AlertDialog dialogo = mbuilder.create();

                            eliminar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDatabase.child(fruta.getKey()).removeValue();
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

        FloatingActionButton fab = vista.findViewById(R.id.fabfruta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment nuevofragmet = new FragmentAgregarFrutaGastronomica();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, nuevofragmet);
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

    public interface FirebaseCallback{
        void onCallback(List<ListaFrutaGastronomica> lista);
    }
}
