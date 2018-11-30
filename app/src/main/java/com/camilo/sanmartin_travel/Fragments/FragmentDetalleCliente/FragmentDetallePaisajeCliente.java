package com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.camilo.sanmartin_travel.Models.ListaPaisaje;
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

public class FragmentDetallePaisajeCliente extends Fragment {

    View vista;

    private GoogleMap mMap;

    ListaPaisaje listaPaisaje;

    private StorageReference mStorage;

    TextView nombre, descripcion, ubicacion;
    ImageView imagePaisaje;
    FloatingActionButton fabPaisaje;

    @SuppressLint("ValidFragment")
    public FragmentDetallePaisajeCliente(ListaPaisaje listaPaisaje) {
        this.listaPaisaje = listaPaisaje;
    }

    public FragmentDetallePaisajeCliente() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_detalle_paisaje_cliente, container, false);

        nombre = vista.findViewById(R.id.NombrePai);
        descripcion = vista.findViewById(R.id.DescripcionPai);
        ubicacion = vista.findViewById(R.id.UbicacionPai);
        fabPaisaje = vista.findViewById(R.id.verMapaPai);
        imagePaisaje = vista.findViewById(R.id.imagesPaisaje);


        fabPaisaje.setOnClickListener(new View.OnClickListener() {
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

                        final Double lat = listaPaisaje .getLat();
                        final Double lng =  listaPaisaje.getLng();

                        final LatLng coordenadas = new LatLng(lat, lng);
                        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 14);
                        mMap.animateCamera(miUbicacion);


                        String descripcionmapa=listaPaisaje.getDescripcionPaisaje();
                        if(descripcionmapa.length()>10){
                            descripcionmapa = descripcionmapa.substring(0,10)+"...";
                        }else{
                            descripcionmapa = descripcionmapa;
                        }
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions()
                                .position(coordenadas)
                                .title(listaPaisaje.getNombrePaisaje())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                                .snippet(descripcionmapa));

                    }
                });

                mbuilder.setView(mView);
                AlertDialog dialog = mbuilder.create();
                dialog.show();

            }
        });


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://san-martin-travel.appspot.com").child(listaPaisaje.getImgPaisaje());

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




        nombre.setText(listaPaisaje.getNombrePaisaje());
        descripcion.setText(listaPaisaje.getDescripcionPaisaje());
        ubicacion.setText(listaPaisaje.getSitioUbicado());


        return vista;
    }
}
