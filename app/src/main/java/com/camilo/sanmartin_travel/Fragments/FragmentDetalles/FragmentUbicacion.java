package com.camilo.sanmartin_travel.Fragments.FragmentDetalles;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentUbicacion extends Fragment {

    View vista;
    private GoogleMap mMap;
    ListaSitios listaSitios;

    @SuppressLint("ValidFragment")
    public FragmentUbicacion(ListaSitios listaSitios) {
        this.listaSitios = listaSitios;
    }

    MapView mapasitio;

    public FragmentUbicacion(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_ubicacion, container, false);


        mapasitio = vista.findViewById(R.id.mapasitio);

        mapasitio.onCreate(savedInstanceState);

        mapasitio.onResume();

        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mapasitio.getMapAsync(new OnMapReadyCallback() {
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

                mMap.setMyLocationEnabled(true);

                final Double lat = listaSitios .getLat();
                final Double lng =  listaSitios.getLng();

                final LatLng coordenadas = new LatLng(lat, lng);
                CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 14);
                mMap.animateCamera(miUbicacion);


                String descripcionmapa=listaSitios.getDescripcion();
                if(descripcionmapa.length()>10){
                    descripcionmapa = descripcionmapa.substring(0,10)+"...";
                }else{
                    descripcionmapa = descripcionmapa;
                }
                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .position(coordenadas)
                        .title(listaSitios.getNombreLugar())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                        .snippet(descripcionmapa));

            }
        });

        return vista;
    }



    @Override
    public void onResume() {
        super.onResume();
        mapasitio.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapasitio.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapasitio.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapasitio.onLowMemory();
    }


}