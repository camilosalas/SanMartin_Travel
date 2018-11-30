package com.camilo.sanmartin_travel.Fragments.Fragment_Administrador;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.camilo.sanmartin_travel.Adapter.AdapterSitioTuristico;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalles.FragmentDetallesSitio;
import com.camilo.sanmartin_travel.Models.ListaSitios;
import com.camilo.sanmartin_travel.Models.SitioTuristico;
import com.camilo.sanmartin_travel.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentListaCentrosTuristicos extends Fragment {

    View vista;

    private DatabaseReference mDatabase;

    String ubicar = "";
    double lat = 0.0;
    double lng = 0.0;

    private GoogleMap mMap;

    ListView ListaSitiosTuristicos;
    List<ListaSitios> listSitios;

    MapView mapa;

    public FragmentListaCentrosTuristicos(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_lista_centros_turisticos, container, false);

        ListaSitiosTuristicos = vista.findViewById(R.id.ListaSitiosTuristicos);

        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("SitiosTuristicos");

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<ListaSitios> lista) {
                ListaSitiosTuristicos.setAdapter(new AdapterSitioTuristico(getActivity(), lista));
            }
        });

        ListaSitiosTuristicos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListaSitios sitios = (ListaSitios) parent.getItemAtPosition(position);
                Fragment nuevofragmet = new FragmentDetallesSitio(sitios);

                Bundle args = new Bundle();
                args.putString("Nombre", sitios.getNombreLugar());
                args.putString("Id", sitios.getKey());
                nuevofragmet.setArguments(args);

                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.principales, nuevofragmet);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ListaSitiosTuristicos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final ListaSitios sitios = (ListaSitios) parent.getItemAtPosition(position);

                final CharSequence[] Opciones = {"Editar", "Eliminar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(sitios.getNombreLugar());
                builder.setIcon(R.drawable.menu);
                builder.setItems(Opciones, new DialogInterface.OnClickListener() {
                    @SuppressLint("IntentReset")
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        if(Opciones[which] == "Editar"){

                            AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
                            @SuppressLint("InflateParams") View mView = getLayoutInflater().inflate(R.layout.dialog_edit_sitio_turistico,null);

                            final EditText nombre = mView.findViewById(R.id.NombreSitioEdit);
                            final EditText descripcion = mView.findViewById(R.id.DescripcionSitioEdit);
                            mapa = mView.findViewById(R.id.mapView);
                            final CardView edit = mView.findViewById(R.id.EditarSitioTuristico);


                            //hecho prueba mapa

                            mapa.onCreate(savedInstanceState);
                            mapa.onResume();

                            try {
                                MapsInitializer.initialize(getActivity());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            mapa.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap googleMap) {

                                    mMap = googleMap;

                                    mMap.setMinZoomPreference(14);
                                    mMap.setIndoorEnabled(true);
                                    UiSettings uiSettings = mMap.getUiSettings();
                                    uiSettings.setIndoorLevelPickerEnabled(true);
                                    uiSettings.setMyLocationButtonEnabled(true);
                                    uiSettings.setMapToolbarEnabled(true);
                                    uiSettings.setCompassEnabled(true);
                                    uiSettings.setZoomControlsEnabled(true);
                                    uiSettings.setScrollGesturesEnabled(true);

                                    if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        return;
                                    } else {
                                        mMap.setMyLocationEnabled(true);
                                    }


                                    //agregado recien

                                    LocationManager locationmanager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                                    Criteria criteria = new Criteria();
                                    assert locationmanager != null;
                                    String provider = locationmanager.getBestProvider(criteria, false);
                                    Location location = locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                                    mMap.setMyLocationEnabled(true);

                                    lat = location.getLatitude();
                                    lng = location.getLongitude();


                                    final Double lat = sitios .getLat();
                                    final Double lng =  sitios.getLng();

                                    final LatLng coordenadas = new LatLng(lat, lng);
                                    CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 14);
                                    mMap.animateCamera(miUbicacion);

                                    mMap.clear();
                                    mMap.addMarker(new MarkerOptions()
                                            .position(coordenadas)
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

                                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                        @Override
                                        public void onMapClick(LatLng latLng) {
                                            mMap.clear();
                                            Toast.makeText(getActivity(), "Marcador Eliminado, Presione un momento sobre el mapa para seleccionar la posición", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                                        @Override
                                        public void onMapLongClick(LatLng latLng) {
                                            mMap.clear();
                                            mMap.addMarker(new MarkerOptions()
                                                    .position(latLng)
                                                    .title("Ubicacion")
                                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                                                    .snippet("Ubicacion del Centro Turístico"));
                                            ubicar = latLng.toString();
                                            Toast.makeText(getActivity().getApplicationContext(), "Coordenadas: " + latLng, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });

                            //fin prueba mapa


                            nombre.setText(sitios.getNombreLugar());
                            descripcion.setText(sitios.getDescripcion());
                            final String img = sitios.getImg();

                            mbuilder.setView(mView);
                            final AlertDialog dialogo = mbuilder.create();

                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    edit.setEnabled(false);

                                    if(!nombre.getText().toString().equals("") && !descripcion.getText().toString().equals("")){

                                        lat = Double.parseDouble(ubicar.substring(10, ubicar.indexOf(",")));
                                        lng = Double.parseDouble(ubicar.substring(ubicar.indexOf(",") + 1, ubicar.length() - 1));

                                        SitioTuristico sitio = new SitioTuristico(nombre.getText().toString(), descripcion.getText().toString(), img, lat, lng);
                                        mDatabase.child(sitios.getKey()).setValue(sitio).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){

                                                    edit.setEnabled(true);
                                                    dialogo.dismiss();
                                                    Toast.makeText(getActivity().getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getActivity().getApplicationContext(), "No se pudo registrar el Sitio Turistico", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    } else{

                                        Toast.makeText(getActivity(), "Por favor rellene el formulario para continuar", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                            dialogo.show();

                        } else if(Opciones[which] == "Eliminar"){
                            AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
                            @SuppressLint("InflateParams") View mView = getLayoutInflater().inflate(R.layout.dialog_delete,null);

                            final TextView nombre = mView.findViewById(R.id.nombreSiitioEliminar);
                            nombre.setText(sitios.getNombreLugar());

                            final CardView eliminar = mView.findViewById(R.id.EliminarSitioTuristico);

                            mbuilder.setView(mView);
                            final AlertDialog dialogo = mbuilder.create();

                            eliminar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDatabase.child(sitios.getKey()).removeValue();
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

        FloatingActionButton fab = vista.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment nuevofragmetCentroT = new FragmentAgregarCentroTuristico();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.principales, nuevofragmetCentroT);
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