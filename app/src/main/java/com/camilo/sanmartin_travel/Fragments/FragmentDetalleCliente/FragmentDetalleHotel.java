package com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.camilo.sanmartin_travel.Models.ListaHoteles;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class FragmentDetalleHotel extends Fragment {

    View vista;

    private GoogleMap mMap;

    ListaHoteles listaHoteles;

    TextView nombre, descripcion, ubicacion, telefono, precio;
    ImageView imagePaisaje;
    ImageButton imgllamadaHotel;
    FloatingActionButton fabHotel;

    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;

    @SuppressLint("ValidFragment")
    public FragmentDetalleHotel(ListaHoteles listaHoteles) {
        this.listaHoteles = listaHoteles;
    }

    public FragmentDetalleHotel(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_detalle_hotel, container, false);

        nombre = vista.findViewById(R.id.NombreHot);
        descripcion = vista.findViewById(R.id.DescripcionHot);
        ubicacion = vista.findViewById(R.id.UbicacionHot);
        telefono = vista.findViewById(R.id.telefonoHot);
        precio = vista.findViewById(R.id.PrecioHot);
        imagePaisaje = vista.findViewById(R.id.imagePaisaje);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://san-martin-travel.appspot.com").child(listaHoteles.getImgHotel());

        try {
            final File localFile = File.createTempFile("images","jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imagePaisaje.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }catch (IOException e){}


        fabHotel = vista.findViewById(R.id.verMapaHot);
        fabHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mbuilder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog_mostrar_mapa,null);

                MapView mapa = mView.findViewById(R.id.mapdialogpai);

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

                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        } else {
                            mMap.setMyLocationEnabled(true);
                        }

                        mMap.setMyLocationEnabled(true);

                        final Double lat = listaHoteles .getLat();
                        final Double lng =  listaHoteles.getLng();

                        final LatLng coordenadas = new LatLng(lat, lng);
                        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 14);
                        mMap.animateCamera(miUbicacion);


                        String descripcionmapa=listaHoteles.getDescripcionHotel();
                        if(descripcionmapa.length()>10){
                            descripcionmapa = descripcionmapa.substring(0,10)+"...";
                        }else{
                            descripcionmapa = descripcionmapa;
                        }
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions()
                                .position(coordenadas)
                                .title(listaHoteles.getNombreHotel())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                                .snippet(descripcionmapa));

                    }
                });

                mbuilder.setView(mView);
                AlertDialog dialog = mbuilder.create();
                dialog.show();
            }
        });


        imgllamadaHotel = vista.findViewById(R.id.imgllamadaHotel);
        imgllamadaHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = ("tel:" + listaHoteles.getTelefonoHotel());
                Intent mIntent = new Intent(Intent.ACTION_CALL);
                mIntent.setData(Uri.parse(number));
                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},MY_PERMISSIONS_REQUEST_CALL_PHONE);
                } else {
                    //You already have permission
                    try {
                        Toast.makeText(getContext(), "LLAMANDO...", Toast.LENGTH_SHORT).show();
                        startActivity(mIntent);
                    } catch(SecurityException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "No puedes realizar llamadas.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        nombre.setText(listaHoteles.getNombreHotel());
        descripcion.setText(listaHoteles.getDescripcionHotel());
        ubicacion.setText(listaHoteles.getUbicacion());
        telefono.setText(listaHoteles.getTelefonoHotel());
        precio.setText(listaHoteles.getPrecio());


        return vista;
    }

}
